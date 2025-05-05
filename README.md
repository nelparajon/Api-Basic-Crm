# CRM BÁSICO API REST

Este proyecto consiste en un CRM básico en **JAVA con SpringBoot** basado en una arquitectura **microservicios**.
Consta de 6 microservicios:

- **Customers**
- **Products**
- **Sales**
- **Users**
- **Api-Gateway**
- **Discovery Service**

Incluye autenticación **JWT** con **Spring Security** en el microservicio **Users**. La **Api Gateway** actúa como punto de acceso a cada microservicio y el servicio de descubrimiento se gestiona con **Eureka Server**. También se ha implementado **paginación** con **Pageable** de Spring donde puede ser más necesario, en este caso al mostrar las **opportunidades de venta** o los **clientes**.

# Tecnologías Usadas

- **Framework**: SpringBoot
- **Arquitectura**: Microservicios
- **Autenticación**: JWT y Spring Security
- **API Gateway**
- **Patrón DTO y Mapper** con **MapStruct**
- **Eureka Server**

## Instalación

1. Clona este repositorio:
```bash
git clone https://github.com/usuario/basicSalesCrm.git

cd basicSalesCrm

mvn clean install

mvn spring-boot:run

## Contribución

Si deseas contribuir, por favor haz un fork del repositorio, crea una nueva rama y envía un pull request con tus cambios.

Asegúrate de seguir las buenas prácticas de codificación y de agregar pruebas si es necesario.

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
