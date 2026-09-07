# COMUNIDEV — Seguridad y Privacidad

## Consideraciones de seguridad

- Las contraseñas deben almacenarse únicamente como hash seguro, por ejemplo BCrypt/Argon2.
- Los JWT deben tener expiración; usar refresh tokens para renovar sesión de forma controlada.
- No exponer CV, certificados, correo, teléfono, ubicación exacta ni datos de contacto sin validar la configuración de privacidad.
- Validar autorización a nivel de caso de uso, no solo en el frontend.
- Verificar que un reclutador realmente pertenezca a la empresa antes de gestionar vacantes o postulaciones.
- Validar tipo, tamaño y contenido permitido de archivos antes de enviarlos a Cloudinary.
- Proteger WebSockets mediante JWT en el handshake o encabezados STOMP.
- Aplicar rate limiting a login, registro, publicación, mensajería y reacciones para mitigar spam.
- Mantener logs de auditoría para acciones administrativas y de moderación.
- No almacenar música con copyright directamente; usar integraciones o referencias permitidas por proveedores autorizados.
