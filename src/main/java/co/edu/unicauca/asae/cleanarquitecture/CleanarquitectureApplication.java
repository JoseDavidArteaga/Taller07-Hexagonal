package co.edu.unicauca.asae.cleanarquitecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import co.edu.unicauca.asae.cleanarquitecture.formatos.aplicacion.input.GestionarFormatoACUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoPPA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.dominio.modelos.FormatoTIA;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.ActualizarEstadoFormatoADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.DocenteDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.DocenteFormatoADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.EstadoDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.EvaluacionDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoADTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoPPADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.FormatoTIADTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.input.dtos.ObservacionDTORespuesta;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.controladorExcepciones.EntidadNoExisteException;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.controladorExcepciones.EntidadYaExisteException;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.controladorExcepciones.ReglaNegocioExcepcion;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.dto.FormatoADetalleDTO;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.entities.DocenteEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EstadoEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.EvaluacionEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.entities.FormatoAEntity;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.EvaluacionRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.formatos.infraestructura.output.repositorios.FormatoARepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.aplicacion.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.dominio.modelos.Docente;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.DocenteDTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios.DocenteRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios.HistoricoRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.output.repositorios.RolRepositoryInt;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.aplicacion.input.GestionarObservacionCUIntPort;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.input.dtos.ObservacionDTOPeticion;
import co.edu.unicauca.asae.cleanarquitecture.observaciones.infraestructura.output.ObservacionRepositoryInt;

@SpringBootApplication
public class CleanarquitectureApplication {

    // Repositorios
    @Autowired(required = false)
    private FormatoARepositoryInt formatoARepository;
    @Autowired(required = false)
    private EvaluacionRepositoryInt evaluacionRepository;
    @Autowired(required = false)
    private DocenteRepositoryInt docenteRepository;
    @Autowired(required = false)
    private ObservacionRepositoryInt observacionRepository;
    @Autowired(required = false)
    private HistoricoRepositoryInt historicoRepository;
    @Autowired(required = false)
    private RolRepositoryInt rolRepository;

    // Casos de uso
    @Autowired(required = false)
    private GestionarFormatoACUIntPort gestionarFormatoACU;
    @Autowired(required = false)
    private GestionarDocenteCUIntPort gestionarDocenteCU;
    @Autowired(required = false)
    private GestionarObservacionCUIntPort gestionarObservacionCU;

    // Validador
    @Autowired(required = false)
    private Validator validator;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private javax.persistence.EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(CleanarquitectureApplication.class, args);
    }

    @Bean
    CommandLineRunner demoRunner() {
        return args -> {
            if (validator == null || gestionarFormatoACU == null || gestionarDocenteCU == null || gestionarObservacionCU == null) {
                System.out.println("CleanarquitectureApplication: dependencias opcionales no disponibles (contexto de test), omitiendo pruebas en CommandLineRunner.");
                return;
            }

            System.out.println("\n============================================================");
            System.out.println("INICIANDO PRUEBAS DESDE CleanarquitectureApplication");
            System.out.println("============================================================\n");

            // PARTE 1: Validaciones automaticas
            try { ejecutarPruebasValidacionesAutomaticas(); } catch (Exception e) { System.out.println("Error en validaciones automaticas: " + e.getMessage()); }

            // PARTE 2: Validacion automatica personalizada
            try { ejecutarPruebasValidacionPersonalizada(); } catch (Exception e) { System.out.println("Error en validacion personalizada: " + e.getMessage()); }

            // PARTE 3: Reglas de negocio
            try { ejecutarPruebasReglasNegocio(); } catch (Exception e) { System.out.println("Error en reglas de negocio: " + e.getMessage()); }

            // PARTE 4: Servicios REST (8 servicios)
            try { ejecutarPruebasServiciosREST(); } catch (Exception e) { System.out.println("Error en servicios REST (servidor no disponible o error de red): " + e.getMessage()); }

            // PARTE 5: Lazy vs Eager
            try { applicationContext.getBean(CleanarquitectureApplication.class).ejecutarPruebasLazyEager(); } catch (Exception e) { System.out.println("Error en pruebas lazy/eager: " + e.getMessage()); }

            System.out.println("\n============================================================");
            System.out.println("PRUEBAS FINALIZADAS");
            System.out.println("============================================================\n");
        };
    }

    // ============================================================
    // 1. PRUEBAS DE VALIDACIONES AUTOMATICAS
    // ============================================================
    private void ejecutarPruebasValidacionesAutomaticas() {
        System.out.println("=== 1. PRUEBAS VALIDACIONES AUTOMATICAS ===\n");

        // 1.1 Titulo nulo
        FormatoADTOPeticion f1 = new FormatoADTOPeticion();
        f1.setTitulo(null);
        f1.setObjetivos(List.of("Analizar requisitos"));
        f1.setDocente(crearDocenteDTOValido());
        validarYDeterner(f1, "titulo", "formatoA.titulo.empty");

        // 1.2 Titulo muy corto
        FormatoADTOPeticion f2 = new FormatoADTOPeticion();
        f2.setTitulo("abc");
        f2.setObjetivos(List.of("Analizar requisitos"));
        f2.setDocente(crearDocenteDTOValido());
        validarYDeterner(f2, "titulo", "formatoA.titulo.size");

        // 1.3 Objetivos vacios
        FormatoADTOPeticion f3 = new FormatoADTOPeticion();
        f3.setTitulo("Formato valido para prueba");
        f3.setObjetivos(new ArrayList<>());
        f3.setDocente(crearDocenteDTOValido());
        validarYDeterner(f3, "objetivos", "formatoA.objetivos.empty");

        // 1.4 Objetivos con menos de 3 elementos
        FormatoADTOPeticion f4 = new FormatoADTOPeticion();
        f4.setTitulo("Formato valido para prueba");
        f4.setObjetivos(List.of("Analizar requisitos"));
        f4.setDocente(crearDocenteDTOValido());
        validarYDeterner(f4, "objetivos", "formatoA.objetivos.min");

        // 1.5 Correo invalido del docente
        DocenteFormatoADTOPeticion docenteInvalido = crearDocenteDTOValido();
        docenteInvalido.setCorreo("correo-invalido");
        FormatoADTOPeticion f5 = new FormatoADTOPeticion();
        f5.setTitulo("Formato valido para prueba");
        f5.setObjetivos(List.of("Analizar requisitos"));
        f5.setDocente(docenteInvalido);
        validarYDeterner(f5, "docente.correo", "formatoA.docente.correo.email");

        // 1.6 Codigo estudiante patron invalido (TIA)
        FormatoTIADTOPeticion tia1 = new FormatoTIADTOPeticion();
        tia1.setTitulo("Formato TIA valido");
        tia1.setObjetivos(List.of("Analizar requisitos"));
        tia1.setDocente(crearDocenteDTOValido());
        tia1.setNombreEstudiante("Juan");
        tia1.setNombreAsesor("Pedro");
        tia1.setCodigoEstudiante("INVALIDO");
        validarYDeterner(tia1, "codigoEstudiante", "formatoA.codigoEstudiante.pattern");

        // 1.7 Descripcion observacion vacia
        ObservacionDTOPeticion obs1 = new ObservacionDTOPeticion();
        obs1.setDescripcion("");
        obs1.setIdFormatoA(1);
        obs1.setIdsDocentes(List.of(1));
        validarYDeterner(obs1, "descripcion", "observacion.descripcion.size");

        // 1.8 Estado vacio (en vez de nulo)
        ActualizarEstadoFormatoADTOPeticion act2 = new ActualizarEstadoFormatoADTOPeticion();
        act2.setEstado("");
        validarYDeterner(act2, "estado", "formatoA.estado.size");

        // 1.9 Estado nulo al actualizar
        ActualizarEstadoFormatoADTOPeticion act1 = new ActualizarEstadoFormatoADTOPeticion();
        act1.setEstado(null);
        validarYDeterner(act1, "estado", "formatoA.estado.empty");

        // 1.10 DocenteDTOPeticion tipoIdentificacion vacio
        DocenteDTOPeticion doc1 = new DocenteDTOPeticion();
        doc1.setTipoIdentificacion("");
        doc1.setNumeroIdentificacion("123");
        doc1.setNombres("Ana");
        doc1.setApellidos("Diaz");
        doc1.setCorreo("ana@unicauca.edu.co");
        doc1.setDepartamento("Sistemas");
        validarYDeterner(doc1, "tipoIdentificacion", "formatoA.docente.tipoIdentificacion.empty");

        System.out.println("  Validaciones automaticas completadas\n");
    }

    private DocenteFormatoADTOPeticion crearDocenteDTOValido() {
        DocenteFormatoADTOPeticion d = new DocenteFormatoADTOPeticion();
        d.setTipoIdentificacion("CC");
        d.setNumeroIdentificacion("123456");
        d.setNombres("Ana");
        d.setApellidos("Diaz");
        d.setCorreo("ana.diaz@unicauca.edu.co");
        d.setDepartamento("Sistemas");
        return d;
    }

    private <T> void validarYDeterner(T objeto, String campoEsperado, String mensajeEsperado) {
        Set<ConstraintViolation<T>> violaciones = validator.validate(objeto);
        boolean encontrado = violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains(campoEsperado)
                        && v.getMessageTemplate().contains(mensajeEsperado));
        if (encontrado) {
            System.out.println("    Violacion detectada en [" + campoEsperado + "]: " + mensajeEsperado);
        } else {
            System.out.println("   XXXXXXXXXXXXX No se encontro violacion esperada en [" + campoEsperado + "]: " + mensajeEsperado);
            if (!violaciones.isEmpty()) {
                violaciones.forEach(v -> System.out.println("    -> " + v.getPropertyPath() + ": " + v.getMessage() + " (template: " + v.getMessageTemplate() + ")"));
            }
        }
    }

    // ============================================================
    // 2. PRUEBAS DE VALIDACION AUTOMATICA PERSONALIZADA (VerboInfinitivo)
    // ============================================================
    private void ejecutarPruebasValidacionPersonalizada() {
        System.out.println("=== 2. PRUEBAS VALIDACION PERSONALIZADA (VerboInfinitivo) ===\n");

        // 2.1 Verbo valido
        FormatoADTOPeticion v1 = new FormatoADTOPeticion();
        v1.setTitulo("Formato con verbos validos");
        v1.setObjetivos(List.of("Analizar requisitos", "Diseñar solucion", "Implementar sistema"));
        v1.setDocente(crearDocenteDTOValido());
        Set<ConstraintViolation<FormatoADTOPeticion>> violaciones1 = validator.validate(v1);
        boolean tieneViolacionesObjetivos1 = violaciones1.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains("objetivos"));
        if (!tieneViolacionesObjetivos1) {
            System.out.println(" Objetivos con verbos infinitivos validos aceptados");
        } else {
            System.out.println("XXXXXXXXXXXXX Se rechazaron verbos infinitivos validos");
        }

        // 2.2 Verbos no reconocidos (no estan en la lista de verbos comunes)
        FormatoADTOPeticion v2 = new FormatoADTOPeticion();
        v2.setTitulo("Formato con verbos no reconocidos");
        v2.setObjetivos(List.of("Correr rapidamente", "Hacer cosas"));
        v2.setDocente(crearDocenteDTOValido());
        Set<ConstraintViolation<FormatoADTOPeticion>> violaciones2 = validator.validate(v2);
        boolean tieneViolacionesObjetivos2 = violaciones2.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains("objetivos")
                        && v.getMessage().contains("verbo"));
        if (tieneViolacionesObjetivos2) {
            System.out.println(" Verbos no reconocidos detectados correctamente");
        } else {
            System.out.println("XXXXXXXXXXXXX No se detectaron verbos no reconocidos");
        }

        // 2.3 Verbo no reconocido que ademas no esta en infinitivo
        FormatoADTOPeticion v3 = new FormatoADTOPeticion();
        v3.setTitulo("Formato con verbo no reconocido");
        v3.setObjetivos(List.of("Volare alto"));
        v3.setDocente(crearDocenteDTOValido());
        Set<ConstraintViolation<FormatoADTOPeticion>> violaciones3 = validator.validate(v3);
        boolean tieneViolacionesObjetivos3 = violaciones3.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains("objetivos")
                        && v.getMessage().contains("verbo"));
        if (tieneViolacionesObjetivos3) {
            System.out.println("Verbo no reconocido detectado correctamente");
        } else {
            System.out.println("XXXXXXXXXXXXX No se detecto verbo no reconocido");
        }

        System.out.println("\nValidacion personalizada completada\n");
    }

    // ============================================================
    // 3. PRUEBAS DE REGLAS DE NEGOCIO
    // ============================================================
    private void ejecutarPruebasReglasNegocio() {
        System.out.println("=== 3. PRUEBAS REGLAS DE NEGOCIO ===\n");

        // Crear datos base usando los casos de uso (evita detached entities)
        Docente docenteBase = new Docente();
        docenteBase.setTipoIdentificacion("CC");
        docenteBase.setNumeroIdentificacion("111111");
        docenteBase.setNombres("Docente");
        docenteBase.setApellidos("Base");
        docenteBase.setCorreo("docente.base@unicauca.edu.co");
        docenteBase.setDepartamento("Sistemas");
        Docente docenteBaseGuardado = gestionarDocenteCU.crear(docenteBase);

        FormatoA formatoBase = new FormatoA();
        formatoBase.setTitulo("Formato Base Reglas");
        formatoBase.setObjetivos(List.of("Analizar requisitos", "Diseñar solucion", "Implementar sistema"));
        formatoBase.setDocente(docenteBaseGuardado);
        FormatoA formatoBaseGuardado = gestionarFormatoACU.crear(formatoBase);

        // Regla 1: No se puede crear un formato A con un titulo que ya existe
        System.out.println("  Regla 1: Titulo duplicado...");
        FormatoA formatoDuplicado = new FormatoA();
        formatoDuplicado.setTitulo(formatoBaseGuardado.getTitulo());
        formatoDuplicado.setObjetivos(List.of("Analizar requisitos", "Diseñar solucion", "Implementar sistema"));
        formatoDuplicado.setDocente(docenteBaseGuardado);
        try {
            gestionarFormatoACU.crear(formatoDuplicado);
            System.out.println("     XXXXXXXXXXXXX No se lanzo excepcion de entidad ya existe");
        } catch (EntidadYaExisteException e) {
            System.out.println("      EntidadYaExisteException lanzada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("      Excepcion lanzada (tipo " + e.getClass().getSimpleName() + "): " + e.getMessage());
        }

        // Regla 2: Un formato A debe tener un docente director
        System.out.println("  Regla 2: Formato sin docente...");
        FormatoA formatoSinDocente = new FormatoA();
        formatoSinDocente.setTitulo("Formato Sin Docente Unico");
        formatoSinDocente.setObjetivos(List.of("Analizar requisitos", "Diseñar solucion", "Implementar sistema"));
        formatoSinDocente.setDocente(null);
        try {
            gestionarFormatoACU.crear(formatoSinDocente);
            System.out.println("     XXXXXXXXXXXXX No se lanzo excepcion de regla de negocio");
        } catch (ReglaNegocioExcepcion e) {
            System.out.println("      ReglaNegocioExcepcion lanzada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("      Excepcion lanzada (tipo " + e.getClass().getSimpleName() + "): " + e.getMessage());
        }

        // Regla 3: No se puede crear un docente con un correo que ya existe
        System.out.println("  Regla 3: Correo duplicado...");
        Docente docenteDuplicado = new Docente();
        docenteDuplicado.setTipoIdentificacion("CC");
        docenteDuplicado.setNumeroIdentificacion("222222");
        docenteDuplicado.setNombres("Otro");
        docenteDuplicado.setApellidos("Docente");
        docenteDuplicado.setCorreo(docenteBaseGuardado.getCorreo());
        docenteDuplicado.setDepartamento("Sistemas");
        try {
            gestionarDocenteCU.crear(docenteDuplicado);
            System.out.println("     XXXXXXXXXXXXX No se lanzo excepcion de entidad ya existe");
        } catch (EntidadYaExisteException e) {
            System.out.println("      EntidadYaExisteException lanzada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("      Excepcion lanzada (tipo " + e.getClass().getSimpleName() + "): " + e.getMessage());
        }

        // Regla 4: Debe especificar al menos un docente que registra la observacion
        System.out.println("  Regla 4: Observacion sin docentes...");
        try {
            gestionarObservacionCU.crear("Observacion sin docentes", formatoBaseGuardado.getIdFormatoA(), new ArrayList<>());
            System.out.println("     XXXXXXXXXXXXX No se lanzo excepcion de regla de negocio");
        } catch (ReglaNegocioExcepcion e) {
            System.out.println("      ReglaNegocioExcepcion lanzada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("      Excepcion lanzada (tipo " + e.getClass().getSimpleName() + "): " + e.getMessage());
        }

        System.out.println("  Reglas de negocio completadas\n");
    }

    // ============================================================
    // 4. PRUEBAS DE SERVICIOS REST (8 servicios)
    // ============================================================
    private void ejecutarPruebasServiciosREST() {
        System.out.println("=== 4. PRUEBAS SERVICIOS REST (8 servicios) ===\n");

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:5000/api";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear docente via REST para usarlo
        DocenteDTOPeticion docentePeticion = new DocenteDTOPeticion();
        docentePeticion.setTipoIdentificacion("CC");
        docentePeticion.setNumeroIdentificacion("333333");
        docentePeticion.setNombres("REST");
        docentePeticion.setApellidos("Tester");
        docentePeticion.setCorreo("rest.tester@unicauca.edu.co");
        docentePeticion.setDepartamento("Sistemas");

        HttpEntity<DocenteDTOPeticion> docenteEntity = new HttpEntity<>(docentePeticion, headers);
        ResponseEntity<co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.DocenteDTORespuesta> docenteResponse =
                restTemplate.postForEntity(baseUrl + "/docentes", docenteEntity,
                        co.edu.unicauca.asae.cleanarquitecture.miembrosComite.infraestructura.input.dtos.DocenteDTORespuesta.class);
        Integer idDocente = docenteResponse.getBody().getIdPersona();
        System.out.println("  Docente creado para REST tests, id: " + idDocente);

        // 4.1 POST /api/formatosA (crear formato generico)
        System.out.println("  Servicio 1: POST /api/formatosA");
        FormatoADTOPeticion formatoPeticion = new FormatoADTOPeticion();
        formatoPeticion.setTitulo("Formato REST Generico");
        formatoPeticion.setObjetivos(List.of("Analizar requisitos", "Diseñar solucion", "Implementar sistema"));
        DocenteFormatoADTOPeticion docenteFormato = crearDocenteDTOValido();
        docenteFormato.setCorreo("rest.docente.formato@unicauca.edu.co");
        docenteFormato.setNumeroIdentificacion("444444");
        formatoPeticion.setDocente(docenteFormato);
        HttpEntity<FormatoADTOPeticion> formatoEntity = new HttpEntity<>(formatoPeticion, headers);
        ResponseEntity<FormatoADTORespuesta> formatoResponse = restTemplate.postForEntity(baseUrl + "/formatosA", formatoEntity, FormatoADTORespuesta.class);
        System.out.println("    Status: " + formatoResponse.getStatusCode() + ", id: " + formatoResponse.getBody().getIdFormatoA());

        // 4.2 POST /api/formatosA/ppa
        System.out.println("  Servicio 2: POST /api/formatosA/ppa");
        FormatoPPADTOPeticion ppaPeticion = new FormatoPPADTOPeticion();
        ppaPeticion.setTitulo("Formato REST PPA");
        ppaPeticion.setObjetivos(List.of("Analizar requisitos", "Diseñar solucion", "Implementar sistema"));
        DocenteFormatoADTOPeticion docentePPA = crearDocenteDTOValido();
        docentePPA.setCorreo("rest.ppa@unicauca.edu.co");
        docentePPA.setNumeroIdentificacion("555555");
        ppaPeticion.setDocente(docentePPA);
        ppaPeticion.setNombreEstudiante("Juan");
        ppaPeticion.setNombreAsesor("Pedro");
        ppaPeticion.setCodigoEstudiante("20IS123");
        HttpEntity<FormatoPPADTOPeticion> ppaEntity = new HttpEntity<>(ppaPeticion, headers);
        ResponseEntity<FormatoADTORespuesta> ppaResponse = restTemplate.postForEntity(baseUrl + "/formatosA/ppa", ppaEntity, FormatoADTORespuesta.class);
        System.out.println("    Status: " + ppaResponse.getStatusCode() + ", id: " + ppaResponse.getBody().getIdFormatoA());

        // 4.3 POST /api/formatosA/tia
        System.out.println("  Servicio 3: POST /api/formatosA/tia");
        FormatoTIADTOPeticion tiaPeticion = new FormatoTIADTOPeticion();
        tiaPeticion.setTitulo("Formato REST TIA");
        tiaPeticion.setObjetivos(List.of("Analizar requisitos", "Diseñar solucion", "Implementar sistema"));
        DocenteFormatoADTOPeticion docenteTIA = crearDocenteDTOValido();
        docenteTIA.setCorreo("rest.tia@unicauca.edu.co");
        docenteTIA.setNumeroIdentificacion("666666");
        tiaPeticion.setDocente(docenteTIA);
        tiaPeticion.setNombreEstudiante("Maria");
        tiaPeticion.setNombreAsesor("Luis");
        tiaPeticion.setCodigoEstudiante("21IS456");
        HttpEntity<FormatoTIADTOPeticion> tiaEntity = new HttpEntity<>(tiaPeticion, headers);
        ResponseEntity<FormatoADTORespuesta> tiaResponse = restTemplate.postForEntity(baseUrl + "/formatosA/tia", tiaEntity, FormatoADTORespuesta.class);
        Integer idFormatoCreado = tiaResponse.getBody().getIdFormatoA();
        System.out.println("    Status: " + tiaResponse.getStatusCode() + ", id: " + idFormatoCreado);

        // 4.4 GET /api/formatosA (listar)
        System.out.println("  Servicio 4: GET /api/formatosA");
        ResponseEntity<FormatoADTORespuesta[]> listarResponse = restTemplate.getForEntity(baseUrl + "/formatosA", FormatoADTORespuesta[].class);
        System.out.println("    Status: " + listarResponse.getStatusCode() + ", cantidad: " + (listarResponse.getBody() != null ? listarResponse.getBody().length : 0));

        // 4.5 GET /api/formatosA/{id}
        System.out.println("  Servicio 5: GET /api/formatosA/" + idFormatoCreado);
        ResponseEntity<FormatoADTORespuesta> consultaResponse = restTemplate.getForEntity(baseUrl + "/formatosA/" + idFormatoCreado, FormatoADTORespuesta.class);
        System.out.println("    Status: " + consultaResponse.getStatusCode() + ", titulo: " + (consultaResponse.getBody() != null ? consultaResponse.getBody().getTitulo() : "null"));

        // 4.6 GET /api/formatosA/docente/{idDocente}
        System.out.println("  Servicio 6: GET /api/formatosA/docente/" + idDocente);
        ResponseEntity<FormatoADTORespuesta[]> docenteFormatosResponse = restTemplate.getForEntity(baseUrl + "/formatosA/docente/" + idDocente, FormatoADTORespuesta[].class);
        System.out.println("    Status: " + docenteFormatosResponse.getStatusCode() + ", cantidad: " + (docenteFormatosResponse.getBody() != null ? docenteFormatosResponse.getBody().length : 0));

        // 4.7 GET /api/formatosA/detalle?titulo=...
        System.out.println("  Servicio 7: GET /api/formatosA/detalle?titulo=Formato REST TIA");
        ResponseEntity<Map> detalleResponse = restTemplate.getForEntity(baseUrl + "/formatosA/detalle?titulo=Formato REST TIA", Map.class);
        System.out.println("    Status: " + detalleResponse.getStatusCode() + ", body: " + (detalleResponse.getBody() != null ? "presente" : "null"));

        // 4.8 PUT /api/formatosA/{id}/estado
        System.out.println("  Servicio 8: PUT /api/formatosA/" + idFormatoCreado + "/estado");
        ActualizarEstadoFormatoADTOPeticion estadoPeticion = new ActualizarEstadoFormatoADTOPeticion();
        estadoPeticion.setEstado("En evaluacion");
        HttpEntity<ActualizarEstadoFormatoADTOPeticion> estadoEntity = new HttpEntity<>(estadoPeticion, headers);
        ResponseEntity<FormatoADTORespuesta> estadoResponse = restTemplate.exchange(baseUrl + "/formatosA/" + idFormatoCreado + "/estado", HttpMethod.PUT, estadoEntity, FormatoADTORespuesta.class);
        System.out.println("    Status: " + estadoResponse.getStatusCode() + ", estado: " + (estadoResponse.getBody() != null && estadoResponse.getBody().getEstado() != null ? estadoResponse.getBody().getEstado().getEstado() : "null"));

        System.out.println("  Servicios REST completados\n");
    }

    // ============================================================
    // 5. PRUEBAS DE LAZY VS EAGER
    // ============================================================
    @Transactional
    public void ejecutarPruebasLazyEager() {
        System.out.println("=== 5. PRUEBAS LAZY VS EAGER ===\n");

        // Crear datos de prueba
        DocenteEntity docente = new DocenteEntity();
        docente.setTipoIdentificacion("CC");
        docente.setNumeroIdentificacion("777777");
        docente.setNombres("Lazy");
        docente.setApellidos("Eager");
        docente.setCorreo("lazy.eager@unicauca.edu.co");
        docente.setDepartamento("Sistemas");
        docente = docenteRepository.save(docente);

        FormatoAEntity formato = new FormatoAEntity();
        formato.setTitulo("Formato Lazy Eager Test");
        formato.setFecha(new Date());
        formato.setObjetivos(List.of("Analizar requisitos", "Diseñar solucion", "Implementar sistema"));
        formato.setDocente(docente);

        EstadoEntity estado = new EstadoEntity();
        estado.setEstado("En formulacion");
        estado.setFormatoA(formato);
        formato.setEstado(estado);

        formato = formatoARepository.save(formato);

        EvaluacionEntity evaluacion = new EvaluacionEntity();
        evaluacion.setConcepto("Aprobado");
        evaluacion.setFechaRegistro(new Date());
        evaluacion.setFormatoA(formato);
        evaluacion = evaluacionRepository.save(evaluacion);

        // Mantener ambos lados de la relación bidireccional en memoria
        formato.setEvaluaciones(new ArrayList<>());
        formato.getEvaluaciones().add(evaluacion);

        Integer idFormato = formato.getIdFormatoA();
        Integer idEvaluacion = evaluacion.getIdEvaluacion();
        Integer idDocente = docente.getIdPersona();

        // Forzar escritura a BD y limpiar el caché de primer nivel para que
        // las siguientes consultas carguen entidades frescas desde la BD.
        // Esto es esencial para poder observar comportamiento LAZY vs EAGER real.
        if (entityManager != null) {
            entityManager.flush();
            entityManager.clear();
        }

        System.out.println("  5.1 Consultar FormatoA por ID (estado EAGER, evaluaciones/docente LAZY)");
        System.out.println("      Esperado: se ve query de FormatoA y Estado (EAGER), pero NO de Evaluaciones ni Docente");
        Optional<FormatoAEntity> formatoOpt = formatoARepository.findById(idFormato);
        if (formatoOpt.isPresent()) {
            FormatoAEntity f = formatoOpt.get();
            boolean estadoInicializado = Hibernate.isInitialized(f.getEstado());
            boolean evaluacionesInicializadas = Hibernate.isInitialized(f.getEvaluaciones());
            boolean docenteInicializado = Hibernate.isInitialized(f.getDocente());
            System.out.println("      -> Estado inicializado (EAGER): " + estadoInicializado + " (esperado: true)");
            System.out.println("      -> Evaluaciones inicializadas (LAZY): " + evaluacionesInicializadas + " (esperado: false)");
            System.out.println("      -> Docente inicializado (LAZY): " + docenteInicializado + " (esperado: false)");
        }

        System.out.println("\n  5.2 Acceder a evaluaciones (debe disparar query LAZY)");
        Optional<FormatoAEntity> formatoOpt2 = formatoARepository.findById(idFormato);
        if (formatoOpt2.isPresent()) {
            FormatoAEntity f = formatoOpt2.get();
            int cantidad = f.getEvaluaciones().size();
            System.out.println("      -> Evaluaciones cargadas bajo demanda: " + cantidad + " (debe ver query adicional en consola)");
        }

        System.out.println("\n  5.3 Consultar con @EntityGraph (findDetalleByIdFormatoA) - debe cargar todo junto");
        System.out.println("      Esperado: query con JOINs para docente, estado y evaluaciones");
        try {
            Optional<FormatoAEntity> formatoDetalle = formatoARepository.findDetalleByIdFormatoA(idFormato);
            if (formatoDetalle.isPresent()) {
                FormatoAEntity f = formatoDetalle.get();
                System.out.println("      -> Formato cargado con EntityGraph. Titulo: " + f.getTitulo());
                System.out.println("      -> Evaluaciones inicializadas: " + Hibernate.isInitialized(f.getEvaluaciones()) + " (esperado: true por EntityGraph)");
            }
        } catch (Exception e) {
            Throwable cause = e;
            boolean isMultipleBag = false;
            while (cause != null) {
                if (cause instanceof org.hibernate.loader.MultipleBagFetchException) {
                    isMultipleBag = true;
                    break;
                }
                cause = cause.getCause();
            }
            if (isMultipleBag) {
                System.out.println("      -> MultipleBagFetchException: Hibernate no permite cargar multiples List (@OneToMany/@ManyToMany) en un solo query.");
                System.out.println("          Esto ocurre porque evaluaciones, observaciones y docentes son bags.");
            } else {
                throw e;
            }
        }

        System.out.println("\n  5.3b Alternativa: EntityGraph simple (docente + estado, sin bags)");
        System.out.println("      Esperado: query con JOIN a Docente y Estado, pero evaluaciones siguen LAZY");
        List<FormatoAEntity> formatosConDocente = formatoARepository.findFormatosConDocenteByDocente_IdPersona(idDocente);
        if (!formatosConDocente.isEmpty()) {
            FormatoAEntity f = formatosConDocente.get(0);
            System.out.println("      -> Formato cargado. Titulo: " + f.getTitulo());
            System.out.println("      -> Docente inicializado: " + Hibernate.isInitialized(f.getDocente()) + " (esperado: true por EntityGraph)");
            System.out.println("      -> Evaluaciones inicializadas: " + Hibernate.isInitialized(f.getEvaluaciones()) + " (esperado: false, LAZY)");
        }

        System.out.println("\n  5.4 Consultar Docente con @EntityGraph (findConFormatosByIdPersona)");
        System.out.println("      Esperado: query que carga docente y sus formatosA juntos");
        Optional<DocenteEntity> docenteConFormatos = docenteRepository.findConFormatosByIdPersona(idDocente);
        if (docenteConFormatos.isPresent()) {
            DocenteEntity d = docenteConFormatos.get();
            System.out.println("      -> Docente cargado. Nombres: " + d.getNombres());
            System.out.println("      -> FormatosA inicializados: " + Hibernate.isInitialized(d.getFormatosA()) + " (esperado: true por EntityGraph)");
        }

        System.out.println("\n  5.5 EvaluacionEntity: observaciones LAZY");
        Optional<EvaluacionEntity> evalOpt = evaluacionRepository.findById(idEvaluacion);
        if (evalOpt.isPresent()) {
            EvaluacionEntity ev = evalOpt.get();
            boolean observacionesInit = Hibernate.isInitialized(ev.getObservaciones());
            System.out.println("      -> Observaciones inicializadas: " + observacionesInit + " (esperado: false, LAZY)");
        }

        System.out.println("\n  Pruebas Lazy/Eager completadas\n");
    }

    // ============================================================
    // METODOS AUXILIARES
    // ============================================================
    @Transactional
    private DocenteEntity crearDocenteEntity(String numeroId, String nombres, String apellidos, String correo, String departamento) {
        Optional<DocenteEntity> existente = docenteRepository.findByCorreo(correo);
        if (existente.isPresent()) {
            return existente.get();
        }
        DocenteEntity d = new DocenteEntity();
        d.setTipoIdentificacion("CC");
        d.setNumeroIdentificacion(numeroId);
        d.setNombres(nombres);
        d.setApellidos(apellidos);
        d.setCorreo(correo);
        d.setDepartamento(departamento);
        return docenteRepository.save(d);
    }

    @Transactional
    private FormatoAEntity crearFormatoAEntity(String titulo, List<String> objetivos, DocenteEntity docente) {
        Optional<FormatoAEntity> existente = formatoARepository.findByTitulo(titulo);
        if (existente.isPresent()) {
            return existente.get();
        }
        FormatoAEntity f = new FormatoAEntity();
        f.setTitulo(titulo);
        f.setFecha(new Date());
        f.setObjetivos(objetivos);
        // Usar getReferenceById para evitar detached entity al persistir formato
        DocenteEntity docenteManaged = docenteRepository.getReferenceById(docente.getIdPersona());
        f.setDocente(docenteManaged);
        return formatoARepository.save(f);
    }

    private Docente mapDocenteEntityADominio(DocenteEntity e) {
        Docente d = new Docente();
        d.setIdPersona(e.getIdPersona());
        d.setTipoIdentificacion(e.getTipoIdentificacion());
        d.setNumeroIdentificacion(e.getNumeroIdentificacion());
        d.setNombres(e.getNombres());
        d.setApellidos(e.getApellidos());
        d.setCorreo(e.getCorreo());
        d.setDepartamento(e.getDepartamento());
        return d;
    }
}
