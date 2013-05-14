package br.gov.serpro.demoiselle.extra.stereotype;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;

@PersistenceController
@RequestScoped
@Stereotype
@Inherited
@Target({ TYPE })
@Retention(RUNTIME)
public @interface DAO {
}
