package ModelLayer;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contratos.class)
public abstract class Contratos_ {

	public static volatile SingularAttribute<Contratos, Date> fechaI;
	public static volatile SingularAttribute<Contratos, Choferes> contIdChof;
	public static volatile SingularAttribute<Contratos, Double> importeTotal;
	public static volatile SingularAttribute<Contratos, Date> fechaF;
	public static volatile SingularAttribute<Contratos, Autos> contIdAuto;
	public static volatile SingularAttribute<Contratos, Usuarios> idUsuario;
	public static volatile SingularAttribute<Contratos, Date> fechaEntrega;
	public static volatile SingularAttribute<Contratos, Turista> contIdTur;
	public static volatile SingularAttribute<Contratos, FormaPago> contIdFormaPago;
	public static volatile SingularAttribute<Contratos, Integer> idContrato;

}

