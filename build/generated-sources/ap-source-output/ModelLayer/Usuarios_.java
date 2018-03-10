package ModelLayer;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuarios.class)
public abstract class Usuarios_ {

	public static volatile SingularAttribute<Usuarios, Integer> idUser;
	public static volatile SingularAttribute<Usuarios, String> apellidos;
	public static volatile ListAttribute<Usuarios, RolUsuario> rolUsuarioList;
	public static volatile SingularAttribute<Usuarios, String> passwordUsuario;
	public static volatile SingularAttribute<Usuarios, String> usuario;
	public static volatile ListAttribute<Usuarios, Contratos> contratosList;
	public static volatile SingularAttribute<Usuarios, String> nombre;
	public static volatile ListAttribute<Usuarios, Trazas> trazasList;

}

