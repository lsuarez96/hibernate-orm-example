package ModelLayer;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FormaPago.class)
public abstract class FormaPago_ {

	public static volatile SingularAttribute<FormaPago, Integer> idPago;
	public static volatile SingularAttribute<FormaPago, String> tipoPago;
	public static volatile ListAttribute<FormaPago, Contratos> contratosList;

}

