package ar.edu.itba.paw.webapp.annotations;

import javax.ws.rs.HttpMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method responds to HTTP PATCH Requests.
 * IMPORTANT! PATCH Annotation was created because JAX-RS does not support it
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("PATCH")
public @interface PATCH {

}
