package ModelLayer;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Choferes.class)
public abstract class Choferes_ {

	public static volatile SingularAttribute<Choferes, String> apellidos;
	public static volatile SingularAttribute<Choferes, Categorias> categoria;
	public static volatile SingularAttribute<Choferes, Integer> idChofer;
	public static volatile SingularAttribute<Choferes, String> numeroId;
	public static volatile SingularAttribute<Choferes, String> direccion;
	public static volatile ListAttribute<Choferes, Contratos> contratosList;
	public static volatile SingularAttribute<Choferes, String> nombre;

}

