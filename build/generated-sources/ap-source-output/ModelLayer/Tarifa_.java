package ModelLayer;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tarifa.class)
public abstract class Tarifa_ {

	public static volatile ListAttribute<Tarifa, Modelos> modelosList;
	public static volatile SingularAttribute<Tarifa, Integer> idTarifa;
	public static volatile SingularAttribute<Tarifa, Double> tarifaNormal;
	public static volatile SingularAttribute<Tarifa, Double> tarifaEspecial;

}

