package ModelLayer;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Situaciones.class)
public abstract class Situaciones_ {

	public static volatile SingularAttribute<Situaciones, Integer> idSit;
	public static volatile SingularAttribute<Situaciones, String> tipoSituacion;
	public static volatile ListAttribute<Situaciones, Autos> autosList;

}

