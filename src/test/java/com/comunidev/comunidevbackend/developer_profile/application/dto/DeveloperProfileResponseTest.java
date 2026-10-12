package com.comunidev.comunidevbackend.developer_profile.application.dto;

import com.comunidev.comunidevbackend.developer_profile.domain.DeveloperProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Bloquea el contrato GraphQL de salida: el dominio usa nombres mas ricos
 * (puesto, grado, credencialUrl, repositorioUrl/demoUrl) y el DTO los traduce
 * a los nombres que el frontend consume (cargo, titulo, nombre, url).
 */
class DeveloperProfileResponseTest {

    @Test
    @DisplayName("Traduce los campos del dominio a los nombres del contrato")
    void traduceCamposDelDominioAlContrato() {
        DeveloperProfile perfil = new DeveloperProfile();
        perfil.setId("p1");
        perfil.setUserId("u1");
        perfil.setTituloProfesional("Full Stack");

        DeveloperProfile.Experiencia experiencia = new DeveloperProfile.Experiencia();
        experiencia.setEmpresa("ACME");
        experiencia.setPuesto("Backend Dev");   // dominio
        experiencia.setDescripcion("APIs");
        experiencia.setFechaInicio("2020-01");
        experiencia.setActual(true);
        perfil.setExperiencias(List.of(experiencia));

        DeveloperProfile.Educacion educacion = new DeveloperProfile.Educacion();
        educacion.setInstitucion("UNI");
        educacion.setGrado("Ingeniero");         // dominio
        educacion.setFechaInicio("2015");
        perfil.setEducacion(List.of(educacion));

        DeveloperProfile.Certificado certificado = new DeveloperProfile.Certificado();
        certificado.setTitulo("AWS SAA");         // dominio ( titulo )
        certificado.setInstitucion("AWS");
        certificado.setCredencialUrl("https://cred");  // dominio
        perfil.setCertificados(List.of(certificado));

        DeveloperProfile.Proyecto proyecto = new DeveloperProfile.Proyecto();
        proyecto.setTitulo("Mi App");             // dominio ( titulo )
        proyecto.setDescripcion("Demo app");
        proyecto.setRepositorioUrl("https://repo");
        proyecto.setDemoUrl("https://demo");      // gana la demo
        proyecto.setTecnologias(List.of("Java"));
        perfil.setProyectos(List.of(proyecto));

        DeveloperProfileResponse response = DeveloperProfileResponse.from(perfil);

        // Experiencia: puesto -> cargo
        assertEquals("Backend Dev", response.experiencias().get(0).cargo());
        // Educacion: grado -> titulo
        assertEquals("Ingeniero", response.educacion().get(0).titulo());
        // Certificado: titulo -> nombre, credencialUrl -> url
        assertEquals("AWS SAA", response.certificados().get(0).nombre());
        assertEquals("https://cred", response.certificados().get(0).url());
        // Proyecto: titulo -> nombre, demoUrl -> url (preferida sobre repo)
        assertEquals("Mi App", response.proyectos().get(0).nombre());
        assertEquals("https://demo", response.proyectos().get(0).url());
    }

    @Test
    @DisplayName("Sin demo, el url del proyecto cae al repositorio")
    void urlDelProyectoCaeAlRepositorioSinDemo() {
        DeveloperProfile.Proyecto proyecto = new DeveloperProfile.Proyecto();
        proyecto.setTitulo("App");
        proyecto.setRepositorioUrl("https://repo");
        proyecto.setDemoUrl("  ");   // en blanco -> se ignora

        DeveloperProfile perfil = new DeveloperProfile();
        perfil.setProyectos(List.of(proyecto));

        DeveloperProfileResponse response = DeveloperProfileResponse.from(perfil);
        assertEquals("https://repo", response.proyectos().get(0).url());
    }

    @Test
    @DisplayName("Listas nulas del dominio se convierten en lista vacia")
    void listasNulasSeVuelvenVacias() {
        DeveloperProfileResponse response = DeveloperProfileResponse.from(new DeveloperProfile());
        assertEquals(List.of(), response.experiencias());
        assertEquals(List.of(), response.educacion());
        assertEquals(List.of(), response.certificados());
        assertEquals(List.of(), response.proyectos());
    }

    @Test
    @DisplayName("from(null) devuelve null para no romper el contrato nullable")
    void fromNullDevuelveNull() {
        assertNull(DeveloperProfileResponse.from(null));
    }
}
