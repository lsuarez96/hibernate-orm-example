package ModelLayer;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Modelos.class)
public abstract class Modelos_ {

	public static volatile SingularAttribute<Modelos, Integer> idModelo;
	public static volatile SingularAttribute<Modelos, Marcas> modeloIdMarca;
	public static volatile SingularAttribute<Modelos, Tarifa> modeloIdTar;
	public static volatile SingularAttribute<Modelos, String> nombreModelo;
	public static volatile ListAttribute<Modelos, Autos> autosList;

}

