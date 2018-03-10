package ModelLayer;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Trazas.class)
public abstract class Trazas_ {

	public static volatile SingularAttribute<Trazas, Date> fecha;
	public static volatile SingularAttribute<Trazas, Integer> idTuplaModificada;
	public static volatile SingularAttribute<Trazas, String> hora;
	public static volatile SingularAttribute<Trazas, Usuarios> idUsuario;
	public static volatile SingularAttribute<Trazas, Integer> idTraza;
	public static volatile SingularAttribute<Trazas, String> operacion;
	public static volatile SingularAttribute<Trazas, String> tablaModificada;
	public static volatile SingularAttribute<Trazas, String> direccionIp;

}

