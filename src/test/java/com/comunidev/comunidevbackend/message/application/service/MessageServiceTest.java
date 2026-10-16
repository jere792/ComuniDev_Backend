package com.comunidev.comunidevbackend.message.application.service;

import com.comunidev.comunidevbackend.follow.application.port.out.FollowRepositoryPort;
import com.comunidev.comunidevbackend.follow.domain.Follow;
import com.comunidev.comunidevbackend.message.application.port.out.MessageRepositoryPort;
import com.comunidev.comunidevbackend.message.domain.Conversation;
import com.comunidev.comunidevbackend.message.domain.Message;
import com.comunidev.comunidevbackend.shared.application.RealtimeEventPublisher;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageServiceTest {

    private static final String ANA = "6aa0c81a986d6702d506f23d";
    private static final String LUIS = "6aa6df4632d88291971df62e";

    private FakeMessageRepository repository;
    private FakeFollowRepository follows;
    private RealtimeEventPublisher realtime;
    private MessageService service;

    @BeforeEach
    void setUp() {
        repository = new FakeMessageRepository();
        follows = new FakeFollowRepository();
        // Por defecto los dos se siguen, para que los tests que no estudian
        // solicitudes caigan en la lista normal.
        follows.seguir(LUIS, ANA);
        follows.seguir(ANA, LUIS);
        realtime = new RealtimeEventPublisher();
        service = new MessageService(repository, follows, realtime);
    }

    @Test
    @DisplayName("enviarMensaje crea el hilo si no existia")
    void enviarMensajeCreaLaConversacion() {
        Message saved = service.enviarMensaje(ANA, LUIS, "Hola");

        assertNotNull(saved.getId());
        assertEquals(1, repository.conversaciones.size());

        Conversation conversation = repository.conversaciones.values().iterator().next();
        assertEquals("Hola", conversation.getUltimoMensajeTexto());
        assertEquals(ANA, conversation.getUltimoMensajeEmisorId());
    }

    @Test
    @DisplayName("enviarMensaje reutiliza el mismo hilo aunque cambie el orden")
    void enviarMensajeReutilizaElHilo() {
        service.enviarMensaje(ANA, LUIS, "Hola");
        service.enviarMensaje(LUIS, ANA, "Que tal?");

        assertEquals(1, repository.conversaciones.size());
        assertEquals(2, repository.mensajes.size());
        assertEquals("Que tal?", repository.conversaciones.values().iterator().next().getUltimoMensajeTexto());
    }

    @Test
    @DisplayName("enviarMensaje deja el mensaje sin leer")
    void enviarMensajeArrancaSinLeer() {
        Message saved = service.enviarMensaje(ANA, LUIS, "Hola");

        assertFalse(saved.getLeido());
    }

    @Test
    @DisplayName("enviarMensaje suma el no leido al receptor, no al emisor")
    void enviarMensajeCuentaParaElReceptor() {
        service.enviarMensaje(ANA, LUIS, "Uno");
        service.enviarMensaje(ANA, LUIS, "Dos");

        Conversation conversation = repository.conversaciones.values().iterator().next();
        assertEquals(2L, conversation.noLeidosPara(LUIS));
        assertEquals(0L, conversation.noLeidosPara(ANA));
    }

    @Test
    @DisplayName("enviarMensaje limpia el no leido de quien responde")
    void enviarMensajeLimpiaElPropio() {
        service.enviarMensaje(LUIS, ANA, "Primero");
        assertEquals(1L, repository.conversaciones.values().iterator().next().noLeidosPara(ANA));

        // Ana no abrio el hilo, pero le respondio: al escribir, lo pendiente se da por visto.
        service.enviarMensaje(ANA, LUIS, "Hola");

        Conversation conversation = repository.conversaciones.values().iterator().next();
        assertEquals(0L, conversation.noLeidosPara(ANA));
        assertEquals(1L, conversation.noLeidosPara(LUIS));
    }

    @Test
    @DisplayName("marcarLeido reinicia el contador de esa persona y deja intacto el del otro")
    void marcarLeidoReiniciaSoloElSuyo() {
        service.enviarMensaje(ANA, LUIS, "Uno");
        service.enviarMensaje(LUIS, ANA, "Hola");

        assertTrue(service.marcarLeido(LUIS, primeraConversacion().getId()));

        Conversation conversation = repository.conversaciones.values().iterator().next();
        assertEquals(0L, conversation.noLeidosPara(LUIS));
        assertEquals(1L, conversation.noLeidosPara(ANA));
    }

    @Test
    @DisplayName("enviarMensaje a uno mismo se rechaza")
    void enviarMensajeASiMismoRechaza() {
        assertThrows(BusinessException.class, () -> service.enviarMensaje(ANA, ANA, "Hola"));
    }

    @Test
    @DisplayName("enviarMensaje vacio se rechaza")
    void enviarMensajeVacioRechaza() {
        assertThrows(BusinessException.class, () -> service.enviarMensaje(ANA, LUIS, "   "));
    }

    @Test
    @DisplayName("enviarMensaje sin emisor se rechaza")
    void enviarMensajeSinEmisorRechaza() {
        assertThrows(BusinessException.class, () -> service.enviarMensaje(null, LUIS, "Hola"));
    }

    @Test
    @DisplayName("enviarMensaje mas largo de lo permitido se rechaza")
    void enviarMensajeLargoRechaza() {
        String muralla = "a".repeat(2001);

        assertThrows(BusinessException.class, () -> service.enviarMensaje(ANA, LUIS, muralla));
    }

    @Test
    @DisplayName("obtenerMensajes devuelve el hilo en orden")
    void obtenerMensajesRespetaElOrden() {
        service.enviarMensaje(ANA, LUIS, "Primero");
        service.enviarMensaje(LUIS, ANA, "Segundo");

        List<Message> messages = service.obtenerMensajes(LUIS, primeraConversacion().getId());

        assertEquals(2, messages.size());
        assertEquals("Primero", messages.get(0).getTexto());
        assertEquals("Segundo", messages.get(1).getTexto());
    }

    @Test
    @DisplayName("obtenerMensajes no deja entrar a quien no participa")
    void obtenerMensajesBloqueaAlAjeno() {
        service.enviarMensaje(ANA, LUIS, "Hola");
        String intruso = "6aa0c415986d6702d506f23c";

        assertTrue(service.obtenerMensajes(intruso, primeraConversacion().getId()).isEmpty());
    }

    @Test
    @DisplayName("obtenerConversaciones solo devuelve las de esa persona")
    void obtenerConversacionesFiltraPorPersona() {
        service.enviarMensaje(ANA, LUIS, "Hola");

        assertEquals(1, service.obtenerConversaciones(ANA).size());
        assertEquals(1, service.obtenerConversaciones(LUIS).size());
        assertTrue(service.obtenerConversaciones("6aa0c415986d6702d506f23c").isEmpty());
    }

    @Test
    @DisplayName("enviarMensaje avisa por el canal de tiempo real a las dos personas")
    void enviarMensajePublicaEnTiempoReal() {
        List<Object> paraAna = new ArrayList<>();
        List<Object> paraLuis = new ArrayList<>();
        realtime.messageStream(ANA).subscribe(paraAna::add);
        realtime.messageStream(LUIS).subscribe(paraLuis::add);

        service.enviarMensaje(ANA, LUIS, "Hola");

        assertEquals(1, paraAna.size());
        assertEquals(1, paraLuis.size());
        assertEquals("Hola", ((Message) paraLuis.get(0)).getTexto());
    }

    @Test
    @DisplayName("marcarLeido devuelve false si el hilo no existe")
    void marcarLeidoHiloInexistente() {
        assertFalse(service.marcarLeido(ANA, "no-existe"));
    }

    @Test
    @DisplayName("enviarMensaje deja el hilo como solicitud cuando el receptor no sigue al emisor")
    void enviarMensajeQuedaComoSolicitud() {
        follows.dejarDeSeguir(LUIS, ANA);

        service.enviarMensaje(ANA, LUIS, "Hola");

        assertTrue(service.obtenerConversaciones(LUIS).isEmpty());
        assertEquals(1, service.obtenerSolicitudes(LUIS).size());
        // El emisor siempre ve su hilo en su lista, sin saber que quedo oculto.
        assertEquals(1, service.obtenerConversaciones(ANA).size());
    }

    @Test
    @DisplayName("si el receptor ya sigue al emisor no hay solicitud")
    void enviarMensajeSinSolicitudSiLoSigue() {
        service.enviarMensaje(ANA, LUIS, "Hola");

        assertTrue(service.obtenerSolicitudes(LUIS).isEmpty());
        assertEquals(1, service.obtenerConversaciones(LUIS).size());
    }

    @Test
    @DisplayName("aceptar pasa la solicitud a conversacion normal")
    void aceptarPasaLaSolicitud() {
        follows.dejarDeSeguir(LUIS, ANA);
        service.enviarMensaje(ANA, LUIS, "Hola");

        assertTrue(service.aceptar(LUIS, primeraConversacion().getId()));

        assertTrue(service.obtenerSolicitudes(LUIS).isEmpty());
        assertEquals(1, service.obtenerConversaciones(LUIS).size());
    }

    @Test
    @DisplayName("rechazar deja de verse para quien rechaza pero el emisor sigue viendolo")
    void rechazarOcultaParaElReceptor() {
        follows.dejarDeSeguir(LUIS, ANA);
        service.enviarMensaje(ANA, LUIS, "Hola");

        assertTrue(service.rechazar(LUIS, primeraConversacion().getId()));

        assertTrue(service.obtenerSolicitudes(LUIS).isEmpty());
        assertTrue(service.obtenerConversaciones(LUIS).isEmpty());
        assertEquals(1, service.obtenerConversaciones(ANA).size());
    }

    @Test
    @DisplayName("responder a una solicitud la acepta implicitamente")
    void responderAceptaLaSolicitud() {
        follows.dejarDeSeguir(LUIS, ANA);
        service.enviarMensaje(ANA, LUIS, "Hola");

        service.enviarMensaje(LUIS, ANA, "Hola! Perdon la demora");

        assertTrue(service.obtenerSolicitudes(LUIS).isEmpty());
        assertEquals(1, service.obtenerConversaciones(LUIS).size());
    }

    @Test
    @DisplayName("escribir de nuevo tras un rechazo vuelve a plantear la solicitud")
    void reescribirTrasRechazoVuelveASolicitud() {
        follows.dejarDeSeguir(LUIS, ANA);
        service.enviarMensaje(ANA, LUIS, "Hola");
        service.rechazar(LUIS, primeraConversacion().getId());

        service.enviarMensaje(ANA, LUIS, "Una consulta");

        assertEquals(1, service.obtenerSolicitudes(LUIS).size());
        assertTrue(service.obtenerConversaciones(LUIS).isEmpty());
    }

    @Test
    @DisplayName("aceptar o rechazar algo que no es solicitud devuelve false")
    void aceptarRechazarSinSolicitud() {
        service.enviarMensaje(ANA, LUIS, "Hola");

        assertFalse(service.aceptar(LUIS, primeraConversacion().getId()));
        assertFalse(service.rechazar(LUIS, primeraConversacion().getId()));
    }

    @Test
    @DisplayName("solicitudes solo devuelve los hilos pendientes de esa persona")
    void solicitudesFiltraPorPersona() {
        follows.dejarDeSeguir(LUIS, ANA);
        service.enviarMensaje(ANA, LUIS, "Hola");

        assertTrue(service.obtenerSolicitudes(ANA).isEmpty());
        assertEquals(1, service.obtenerSolicitudes(LUIS).size());
    }

    private Conversation primeraConversacion() {
        return repository.conversaciones.values().iterator().next();
    }

    /** Repositorio en memoria, para no depender de MongoDB en una prueba de unidad. */
    private static class FakeMessageRepository implements MessageRepositoryPort {

        private final Map<String, Conversation> conversaciones = new LinkedHashMap<>();
        private final List<Message> mensajes = new ArrayList<>();
        private int secuenciaConversacion = 0;
        private int secuenciaMensaje = 0;

        @Override
        public Conversation saveConversation(Conversation conversation) {
            if (conversation.getId() == null) {
                secuenciaConversacion++;
                conversation.setId("c" + secuenciaConversacion);
            }
            conversaciones.put(conversation.getId(), conversation);
            return conversation;
        }

        @Override
        public Optional<Conversation> findConversationById(String id) {
            return Optional.ofNullable(conversaciones.get(id));
        }

        @Override
        public Optional<Conversation> findConversationByClave(String clave) {
            return conversaciones.values().stream()
                    .filter(c -> clave.equals(c.getClave()))
                    .findFirst();
        }

        @Override
        public List<Conversation> findConversationsByUserId(String userId) {
            return conversaciones.values().stream()
                    .filter(c -> c.getParticipantes().contains(userId))
                    .sorted((a, b) -> {
                        Instant x = a.getUltimoMensajeEn();
                        Instant y = b.getUltimoMensajeEn();
                        if (x == null) return 1;
                        if (y == null) return -1;
                        return y.compareTo(x);
                    })
                    .toList();
        }

        @Override
        public List<Conversation> findConversacionesVisibles(String userId) {
            return findConversationsByUserId(userId).stream()
                    .filter(c -> !c.esSolicitudPara(userId) && !c.esArchivadaPara(userId))
                    .toList();
        }

        @Override
        public Message saveMessage(Message message) {
            if (message.getId() == null) {
                secuenciaMensaje++;
                message.setId("m" + secuenciaMensaje);
            }
            mensajes.add(message);
            return message;
        }

        @Override
        public List<Message> findMessagesByConversationId(String conversationId) {
            return mensajes.stream()
                    .filter(m -> conversationId.equals(m.getConversationId()))
                    .sorted(Comparator.comparing(Message::getCreatedAt))
                    .toList();
        }
    }

    /** Follows en memoria: solo el par que importa, sin tocar MongoDB. */
    private static class FakeFollowRepository implements FollowRepositoryPort {

        private final Set<String> pares = new HashSet<>();

        void seguir(String seguidorId, String seguidoId) {
            pares.add(clave(seguidorId, seguidoId));
        }

        void dejarDeSeguir(String seguidorId, String seguidoId) {
            pares.remove(clave(seguidorId, seguidoId));
        }

        @Override
        public Follow save(Follow follow) {
            pares.add(clave(follow.getSeguidorId(), follow.getSeguidoId()));
            return follow;
        }

        @Override
        public List<Follow> findBySeguidorId(String seguidorId) {
            return List.of();
        }

        @Override
        public List<Follow> findBySeguidoId(String seguidoId) {
            return List.of();
        }

        @Override
        public Optional<Follow> findBySeguidorIdAndSeguidoId(String seguidorId, String seguidoId) {
            return pares.contains(clave(seguidorId, seguidoId))
                    ? Optional.of(Follow.create(seguidorId, "user", seguidoId))
                    : Optional.empty();
        }

        @Override
        public void delete(Follow follow) {
            pares.remove(clave(follow.getSeguidorId(), follow.getSeguidoId()));
        }

        private String clave(String seguidorId, String seguidoId) {
            return seguidorId + "|" + seguidoId;
        }
    }
}
