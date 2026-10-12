package com.comunidev.comunidevbackend.connection.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConnectionTest {

    @Test
    @DisplayName("create() marca la conexion como ACTIVA y registra el iniciador")
    void createMarcaActiva() {
        Connection connection = Connection.create("id-a", "id-b", "id-a");

        assertEquals("ACTIVA", connection.getEstado());
        assertEquals("id-a", connection.getIniciadorId());
        assertNotNull(connection.getCreatedAt());
        assertNotNull(connection.getUpdatedAt());
    }

    @Test
    @DisplayName("la normalizacion menor/mayor produce la misma clave en cualquier orden de entrada")
    void ordenMenorMayorEsCanonico() {
        String a = "6a9f94600800afc75b38dd99";
        String b = "6a9f9903212145871d81ec45";

        // Misma logica que usa ConnectionGraphQLResolver.acceptConnection().
        String[] parA = normalizar(a, b);
        String[] parB = normalizar(b, a);

        assertEquals(parA[0], parB[0], "el usuario menor debe ser el mismo venga como venga");
        assertEquals(parA[1], parB[1], "el usuario mayor debe ser el mismo venga como venga");
        assertEquals(a.compareTo(b) < 0 ? a : b, parA[0]);
        assertEquals(a.compareTo(b) < 0 ? b : a, parA[1]);
    }

    private String[] normalizar(String id1, String id2) {
        String menor = id1.compareTo(id2) < 0 ? id1 : id2;
        String mayor = id1.compareTo(id2) < 0 ? id2 : id1;
        return new String[]{menor, mayor};
    }

    @Test
    @DisplayName("ConnectionRequest arranca PENDIENTE y sin fecha de respuesta")
    void requestArrancaPendiente() {
        ConnectionRequest request = ConnectionRequest.create("solicitante", "receptor", "hola");

        assertEquals("PENDIENTE", request.getEstado());
        assertEquals("hola", request.getMensaje());
        assertNull(request.getRespondidoEn(), "una solicitud nueva no debe tener fecha de respuesta");
    }
}
