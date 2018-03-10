package ModelLayer;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Turista.class)
public abstract class Turista_ {

	public static volatile SingularAttribute<Turista, Integer> idTur;
	public static volatile SingularAttribute<Turista, String> apellidos;
	public static volatile SingularAttribute<Turista, String> pasaporte;
	public static volatile ListAttribute<Turista, Contratos> contratosList;
	public static volatile SingularAttribute<Turista, String> sexo;
	public static volatile SingularAttribute<Turista, String> telefono;
	public static volatile SingularAttribute<Turista, String> nombre;
	public static volatile SingularAttribute<Turista, Integer> edad;
	public static volatile SingularAttribute<Turista, Pais> turIdPais;

}

