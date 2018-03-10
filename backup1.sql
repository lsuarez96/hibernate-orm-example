--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.0
-- Dumped by pg_dump version 9.3.0
-- Started on 2017-07-27 02:21:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 211 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2320 (class 0 OID 0)
-- Dependencies: 211
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 218 (class 1255 OID 24577)
-- Name: actualizar_situacion(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION actualizar_situacion() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
UPDATE autos set id_situacion_auto=(SELECT situaciones.id_sit FROM situaciones WHERE situaciones.tipo_situacion LIKE '%libre')::INTEGER
WHERE OLD.cont_id_auto=autos.id_auto;
RETURN NEW;
END
$$;


ALTER FUNCTION public.actualizar_situacion() OWNER TO postgres;

--
-- TOC entry 225 (class 1255 OID 24578)
-- Name: alquilar_dos_carros_el_mismo_dia(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION alquilar_dos_carros_el_mismo_dia() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
fecha date;
BEGIN
SELECT contratos.fecha_i INTO fecha FROM contratos
WHERE contratos.cont_id_tur=NEW.cont_id_tur AND contratos.fecha_entrega ISNULL
ORDER BY  contratos.fecha_i DESC;

if(fecha NOTNULL)THEN
	if(fecha=NEW.fecha_i)THEN
		raise EXCEPTION 'No puede alquilar mas de un carro el mismo dia';
	END IF;
END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION public.alquilar_dos_carros_el_mismo_dia() OWNER TO postgres;

--
-- TOC entry 226 (class 1255 OID 24579)
-- Name: buscar_auto(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_auto(character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT autos.chapa,
modelos.nombre_modelo,
marcas.nombre_marca,
situaciones.tipo_situacion,
autos.color,
autos.kilometros,
autos.id_auto
 FROM autos JOIN modelos ON autos.id_modelo_auto=modelos.id_modelo
JOIN marcas on marcas.id_marca=modelos.modelo_id_marca JOIN situaciones ON
situaciones.id_sit=autos.id_situacion_auto
 WHERE autos.chapa=$1;
RETURN cur;
END
$_$;


ALTER FUNCTION public.buscar_auto(character varying) OWNER TO postgres;

--
-- TOC entry 227 (class 1255 OID 24580)
-- Name: buscar_auto(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_auto(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT autos.chapa,
modelos.nombre_modelo,
marcas.nombre_marca,
situaciones.tipo_situacion,
autos.color,
autos.kilometros,
autos.id_auto
 FROM autos JOIN modelos ON autos.id_modelo_auto=modelos.id_modelo
JOIN marcas on marcas.id_marca=modelos.modelo_id_marca JOIN situaciones ON
situaciones.id_sit=autos.id_situacion_auto
 WHERE autos.id_auto=$1;
RETURN cur;
END
$_$;


ALTER FUNCTION public.buscar_auto(integer) OWNER TO postgres;

--
-- TOC entry 228 (class 1255 OID 24581)
-- Name: buscar_autos(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_autos() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
OPEN cur FOR
SELECT autos.chapa,modelos.nombre_modelo,marcas.nombre_marca,situaciones.tipo_situacion,autos.color,autos.kilometros,
autos.id_auto
 FROM autos JOIN modelos ON autos.id_modelo_auto=modelos.id_modelo
JOIN marcas on marcas.id_marca=modelos.modelo_id_marca JOIN situaciones ON
situaciones.id_sit=autos.id_situacion_auto;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_autos() OWNER TO postgres;

--
-- TOC entry 229 (class 1255 OID 24582)
-- Name: buscar_categorias(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_categorias() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT categorias.tipo_categoria FROM categorias;

RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_categorias() OWNER TO postgres;

--
-- TOC entry 230 (class 1255 OID 24583)
-- Name: buscar_choferes(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_choferes() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
OPEN cur FOR
SELECT choferes.numero_id,
choferes.nombre,
choferes.apellidos,
categorias.tipo_categoria,
choferes.direccion,
choferes.id_chofer
FROM choferes JOIN categorias ON choferes.categoria=categorias.id_cat;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_choferes() OWNER TO postgres;

--
-- TOC entry 231 (class 1255 OID 24584)
-- Name: buscar_contrato(character varying, character varying, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_contrato(plate character varying, tur character varying, fi date) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
var refcursor;
BEGIN
open var for 
SELECT autos.chapa,
turista.pasaporte,
contratos.fecha_i,
contratos.fecha_f,
choferes.numero_id,
contratos.fecha_entrega,
forma_pago.tipo_pago,
contratos.importe_total
  FROM contratos JOIN autos ON contratos.cont_id_auto=autos.id_auto 
JOIN turista ON turista.id_tur=contratos.cont_id_tur JOIN forma_pago
ON contratos.cont_id_forma_pago=forma_pago.id_pago join choferes on contratos.cont_id_chof=choferes.id_chofer
WHERE autos.chapa=plate AND turista.pasaporte=tur AND contratos.fecha_i=fi;
RETURN var;
END
$$;


ALTER FUNCTION public.buscar_contrato(plate character varying, tur character varying, fi date) OWNER TO postgres;

--
-- TOC entry 232 (class 1255 OID 24585)
-- Name: buscar_contratos(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_contratos() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
OPEN cur FOR 
SELECT autos.chapa,
turista.pasaporte,
contratos.fecha_i,
contratos.fecha_f,
identidad_del_chofer(contratos.cont_id_chof),
contratos.fecha_entrega,
forma_pago.tipo_pago,
contratos.importe_total
  FROM contratos JOIN autos ON contratos.cont_id_auto=autos.id_auto 
JOIN turista ON turista.id_tur=contratos.cont_id_tur JOIN forma_pago
ON contratos.cont_id_forma_pago=forma_pago.id_pago;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_contratos() OWNER TO postgres;

--
-- TOC entry 233 (class 1255 OID 24586)
-- Name: buscar_formas_de_pago(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_formas_de_pago() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT forma_pago.tipo_pago FROM forma_pago;

RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_formas_de_pago() OWNER TO postgres;

--
-- TOC entry 234 (class 1255 OID 24587)
-- Name: buscar_marcas(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_marcas() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT marcas.nombre_marca FROM marcas;

RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_marcas() OWNER TO postgres;

--
-- TOC entry 235 (class 1255 OID 24588)
-- Name: buscar_modelo(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_modelo(character varying, character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
DECLARE 
cur refcursor;
BEGIN
OPEN cur FOR
SELECT modelos.nombre_modelo,marcas.nombre_marca,
(tarifa.tarifa_normal)::VARCHAR,(tarifa.tarifa_especial)::VARCHAR
 FROM modelos JOIN marcas ON modelos.modelo_id_marca=marcas.id_marca
JOIN tarifa on tarifa.id_tarifa=modelos.modelo_id_tar
WHERE modelos.nombre_modelo=$1 and marcas.nombre_marca=$2;
RETURN cur;
END
$_$;


ALTER FUNCTION public.buscar_modelo(character varying, character varying) OWNER TO postgres;

--
-- TOC entry 236 (class 1255 OID 24589)
-- Name: buscar_modelos(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_modelos() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE 
cur refcursor;
BEGIN
OPEN cur FOR
SELECT modelos.nombre_modelo,
marcas.nombre_marca,(tarifa.tarifa_normal)::VARCHAR,(tarifa.tarifa_especial)::VARCHAR
 FROM modelos JOIN marcas ON modelos.modelo_id_marca=marcas.id_marca 
JOIN tarifa on tarifa.id_tarifa=modelos.modelo_id_tar;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_modelos() OWNER TO postgres;

--
-- TOC entry 237 (class 1255 OID 24590)
-- Name: buscar_pais(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_pais(character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
DECLARE

cur refcursor;
BEGIN
OPEN cur for select pais.nombre_pais FROM pais WHERE nombre_pais=$1;
RETURN cur;
END
$_$;


ALTER FUNCTION public.buscar_pais(character varying) OWNER TO postgres;

--
-- TOC entry 238 (class 1255 OID 24591)
-- Name: buscar_paises(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_paises() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT pais.nombre_pais FROM pais;

RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_paises() OWNER TO postgres;

--
-- TOC entry 239 (class 1255 OID 24592)
-- Name: buscar_rol_usuario(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_rol_usuario(tipo character varying, usern character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
id_r int;
id_u int;
BEGIN
SELECT rol.id_rol INTO id_r FROM rol WHERE rol.tipo_rol=tipo;
SELECT usuarios.id_user INTO id_u FROM usuarios WHERE userN=usuarios.usuario;
OPEN cur FOR
SELECT rol.tipo_rol,
usuarios.usuario
FROM rol_usuario JOIN rol ON rol_usuario.rol_id_rol=rol.id_rol
JOIN usuarios on rol_usuario.usuario_id_user=usuarios.id_user
WHERE usuarios.id_user=id_u AND rol.id_rol=id_r;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_rol_usuario(tipo character varying, usern character varying) OWNER TO postgres;

--
-- TOC entry 240 (class 1255 OID 24593)
-- Name: buscar_roles(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_roles() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT rol.tipo_rol FROM rol;

RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_roles() OWNER TO postgres;

--
-- TOC entry 241 (class 1255 OID 24594)
-- Name: buscar_roles_asosiados(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_roles_asosiados(character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
DECLARE
cur refcursor;
BEGIN
OPEN cur for
SELECT rol.id_rol,rol.tipo_rol FROM rol JOIN rol_usuario ON rol.id_rol=rol_usuario.rol_id_rol
JOIN usuarios ON usuarios.id_user=rol_usuario.usuario_id_user
WHERE usuarios.usuario=$1;
RETURN cur;
END
$_$;


ALTER FUNCTION public.buscar_roles_asosiados(character varying) OWNER TO postgres;

--
-- TOC entry 242 (class 1255 OID 24595)
-- Name: buscar_roles_usuarios(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_roles_usuarios() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
OPEN cur FOR
SELECT rol.tipo_rol,
usuarios.usuario
FROM rol_usuario JOIN rol ON rol_usuario.rol_id_rol=rol.id_rol
JOIN usuarios on rol_usuario.usuario_id_user=usuarios.id_user;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_roles_usuarios() OWNER TO postgres;

--
-- TOC entry 243 (class 1255 OID 24596)
-- Name: buscar_situaciones(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_situaciones() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT situaciones.tipo_situacion FROM situaciones;

RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_situaciones() OWNER TO postgres;

--
-- TOC entry 244 (class 1255 OID 24597)
-- Name: buscar_tarifa(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_tarifa(tar_n character varying, tar_e character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT 
(tarifa.tarifa_normal)::VARCHAR,(tarifa.tarifa_especial)::VARCHAR
FROM tarifa
WHERE tarifa.tarifa_normal=(tar_n)::money AND tarifa.tarifa_especial=(tar_e)::money;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_tarifa(tar_n character varying, tar_e character varying) OWNER TO postgres;

--
-- TOC entry 245 (class 1255 OID 24598)
-- Name: buscar_tarifas(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_tarifas() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
open cur for 
SELECT 
(tarifa.tarifa_normal)::VARCHAR,(tarifa.tarifa_especial)::VARCHAR
FROM tarifa ;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_tarifas() OWNER TO postgres;

--
-- TOC entry 246 (class 1255 OID 24599)
-- Name: buscar_turista(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_turista(pass character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
OPEN cur FOR
SELECT turista.pasaporte,
turista.nombre,
turista.apellidos,
turista.edad,
turista.telefono,
turista.sexo,
pais.nombre_pais,
turista.id_tur
 FROM turista JOIN pais on pais.id_pais=turista.tur_id_pais
WHERE turista.pasaporte=pass;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_turista(pass character varying) OWNER TO postgres;

--
-- TOC entry 247 (class 1255 OID 24600)
-- Name: buscar_turistas(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_turistas() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
OPEN cur FOR
SELECT turista.pasaporte,
turista.nombre,
turista.apellidos,
turista.edad,
turista.telefono,
turista.sexo,
pais.nombre_pais,
turista.id_tur
 FROM turista JOIN pais on pais.id_pais=turista.tur_id_pais;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_turistas() OWNER TO postgres;

--
-- TOC entry 248 (class 1255 OID 24601)
-- Name: buscar_usuario(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_usuario(character varying, character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
DECLARE
cur refcursor;
BEGIN
OPEN cur FOR 
SELECT * FROM usuarios WHERE usuarios.usuario=$1 AND usuarios.password_usuario=$2;
RETURN cur;
END
$_$;


ALTER FUNCTION public.buscar_usuario(character varying, character varying) OWNER TO postgres;

--
-- TOC entry 249 (class 1255 OID 24602)
-- Name: buscar_usuarios(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION buscar_usuarios() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
cur refcursor;
BEGIN
OPEN cur FOR 
SELECT usuarios.id_user, usuarios.usuario,usuarios.password_usuario,usuarios.nombre,usuarios.apellidos FROM usuarios;
RETURN cur;
END
$$;


ALTER FUNCTION public.buscar_usuarios() OWNER TO postgres;

--
-- TOC entry 250 (class 1255 OID 24603)
-- Name: calcular_dias_prorroga_contrato(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_dias_prorroga_contrato(id_con integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
dia int;
ff date;
fe date;
BEGIN
SELECT contratos.fecha_f into ff FROM contratos WHERE contratos.id_contrato=id_con;
SELECT contratos.fecha_entrega into fe FROM contratos WHERE contratos.id_contrato=id_con;
if(fe ISNULL)THEN
dia=0;
ELSIF(fe<ff)THEN
dia=0;
ELSE
dia=(fe-ff)::INT;
END IF;
RETURN dia;
END
$$;


ALTER FUNCTION public.calcular_dias_prorroga_contrato(id_con integer) OWNER TO postgres;

--
-- TOC entry 252 (class 1255 OID 24604)
-- Name: calcular_importe(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_importe(id_cont integer) RETURNS real
    LANGUAGE plpgsql
    AS $$
DECLARE
total float;
aux float;
tar_n float;
tar_e float;
id_ta INT;
fi date;
ff date;
fe date;
BEGIN
total=0;

SELECT tarifa.id_tarifa INTO id_ta 
FROM tarifa JOIN modelos on modelos.modelo_id_tar=tarifa.id_tarifa
JOIN autos ON autos.id_modelo_auto=modelos.id_modelo JOIN
 contratos on contratos.cont_id_auto=autos.id_auto
WHERE contratos.id_contrato=id_cont;

SELECT (tarifa.tarifa_normal) INTO tar_n FROM tarifa WHERE id_tarifa=id_ta;
SELECT (tarifa.tarifa_especial) INTO tar_e FROM tarifa WHERE id_tarifa=id_ta;

SELECT contratos.fecha_f INTO ff FROM contratos WHERE id_contrato=id_cont;
SELECT contratos.fecha_i INTO fi FROM contratos WHERE id_contrato=id_cont; 
SELECT contratos.fecha_entrega INTO fe FROM contratos WHERE id_contrato=id_cont;

if(fe NOTNULL)THEN
if(ff>fe) THEN
total:=((fe-fi)*tar_n)::float;
ELSE
aux:=((fe-ff)*tar_e)::float;
total:=((ff-fi)*tar_n)::float;
total:=(total+aux)::float;
END IF;
ELSE
total:=((ff-fi)*tar_n)::float;
END IF;
RETURN total;
END
$$;


ALTER FUNCTION public.calcular_importe(id_cont integer) OWNER TO postgres;

--
-- TOC entry 253 (class 1255 OID 24605)
-- Name: calcular_importe2(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_importe2(id_cont integer) RETURNS double precision
    LANGUAGE plpgsql
    AS $$
DECLARE
total float;
aux float;
tar_n float;
tar_e float;
id_ta INT;
fi date;
ff date;
fe date;
BEGIN
total=0;

SELECT tarifa.id_tarifa INTO id_ta 
FROM tarifa JOIN modelos on modelos.modelo_id_tar=tarifa.id_tarifa
JOIN autos ON autos.id_modelo_auto=modelos.id_modelo JOIN
 contratos on contratos.cont_id_auto=autos.id_auto
WHERE contratos.id_contrato=id_cont;

SELECT (tarifa.tarifa_normal) INTO tar_n FROM tarifa WHERE id_tarifa=id_ta;
SELECT (tarifa.tarifa_especial) INTO tar_e FROM tarifa WHERE id_tarifa=id_ta;

SELECT contratos.fecha_f INTO ff FROM contratos WHERE id_contrato=id_cont;
SELECT contratos.fecha_i INTO fi FROM contratos WHERE id_contrato=id_cont; 
SELECT contratos.fecha_entrega INTO fe FROM contratos WHERE id_contrato=id_cont;

if(fe NOTNULL)THEN
if(ff>fe) THEN
total:=((fe-fi)*tar_n)::float;
ELSE
aux:=((fe-ff)*tar_e)::float;
total:=((ff-fi)*tar_n)::float;
total:=(total+aux)::float;
END IF;
ELSE
total:=((ff-fi)*tar_n)::float;
END IF;
RETURN total;
END
$$;


ALTER FUNCTION public.calcular_importe2(id_cont integer) OWNER TO postgres;

--
-- TOC entry 254 (class 1255 OID 24606)
-- Name: calcular_importe_cheque(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_importe_cheque(model character varying, marc character varying) RETURNS real
    LANGUAGE plpgsql
    AS $$
DECLARE
total float:=0;
id_mo INT;
id_ma int;
id_fp int;
BEGIN
SELECT marcas.id_marca INTO id_ma FROM marcas WHERE marcas.nombre_marca=marc;
SELECT modelos.id_modelo INTO id_mo FROM modelos WHERE modelos.nombre_modelo=model and modelos.modelo_id_marca=id_ma;
SELECT forma_pago.id_pago INTO id_fp FROM forma_pago WHERE forma_pago.tipo_pago='cheque';

SELECT sum(contratos.importe_total) into total 
FROM contratos JOIN autos ON contratos.cont_id_auto=autos.id_auto 
JOIN modelos ON autos.id_modelo_auto=modelos.id_modelo JOIN marcas ON marcas.id_marca=modelos.modelo_id_marca
WHERE marcas.id_marca=id_ma AND modelos.id_modelo=id_mo and contratos.cont_id_forma_pago=id_fp;
if(total ISNULL)THEN
total=0;
END IF;
RETURN total;
END
$$;


ALTER FUNCTION public.calcular_importe_cheque(model character varying, marc character varying) OWNER TO postgres;

--
-- TOC entry 255 (class 1255 OID 24607)
-- Name: calcular_importe_efectivo(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_importe_efectivo(model character varying, marc character varying) RETURNS real
    LANGUAGE plpgsql
    AS $$
DECLARE
total float:=0;
id_mo INT;
id_ma int;
id_fp int;
BEGIN
SELECT marcas.id_marca INTO id_ma FROM marcas WHERE marcas.nombre_marca=marc;
SELECT modelos.id_modelo INTO id_mo FROM modelos WHERE modelos.nombre_modelo=model and modelos.modelo_id_marca=id_ma;
SELECT forma_pago.id_pago INTO id_fp FROM forma_pago WHERE forma_pago.tipo_pago='efectivo';

SELECT sum(contratos.importe_total) into total 
FROM contratos JOIN autos ON contratos.cont_id_auto=autos.id_auto 
JOIN modelos ON autos.id_modelo_auto=modelos.id_modelo JOIN marcas ON marcas.id_marca=modelos.modelo_id_marca
WHERE marcas.id_marca=id_ma AND modelos.id_modelo=id_mo and contratos.cont_id_forma_pago=id_fp;
if(total ISNULL)THEN
total=0;
END IF;
RETURN total;
END
$$;


ALTER FUNCTION public.calcular_importe_efectivo(model character varying, marc character varying) OWNER TO postgres;

--
-- TOC entry 256 (class 1255 OID 24608)
-- Name: calcular_importe_marca(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_importe_marca(character varying) RETURNS real
    LANGUAGE plpgsql
    AS $_$
DECLARE
total float;
BEGIN
SELECT sum(contratos.importe_total) INTO total 
FROM contratos JOIN autos 
								ON contratos.cont_id_auto=autos.id_auto 
JOIN modelos ON autos.id_modelo_auto=modelos.id_modelo JOIN marcas ON marcas.id_marca=modelos.modelo_id_marca
WHERE marcas.nombre_marca=$1;
if(total ISNULL)THEN
total=0;
END IF;
RETURN total;
END
$_$;


ALTER FUNCTION public.calcular_importe_marca(character varying) OWNER TO postgres;

--
-- TOC entry 257 (class 1255 OID 24609)
-- Name: calcular_importe_tarjeta(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_importe_tarjeta(model character varying, marc character varying) RETURNS real
    LANGUAGE plpgsql
    AS $$
DECLARE
total float:=0;
id_mo INT;
id_ma int;
id_fp int;
BEGIN
SELECT marcas.id_marca INTO id_ma FROM marcas WHERE marcas.nombre_marca=marc;
SELECT modelos.id_modelo INTO id_mo FROM modelos WHERE modelos.nombre_modelo=model and modelos.modelo_id_marca=id_ma;
SELECT forma_pago.id_pago INTO id_fp FROM forma_pago WHERE forma_pago.tipo_pago='tarjeta de credito';

SELECT sum(contratos.importe_total) into total 
FROM contratos JOIN autos ON contratos.cont_id_auto=autos.id_auto 
JOIN modelos ON autos.id_modelo_auto=modelos.id_modelo JOIN marcas ON marcas.id_marca=modelos.modelo_id_marca
WHERE marcas.id_marca=id_ma AND modelos.id_modelo=id_mo and contratos.cont_id_forma_pago=id_fp;
if(total ISNULL)THEN
total=0;
END IF;
RETURN total;
END
$$;


ALTER FUNCTION public.calcular_importe_tarjeta(model character varying, marc character varying) OWNER TO postgres;

--
-- TOC entry 258 (class 1255 OID 24610)
-- Name: calcular_importe_total(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_importe_total(model character varying, marc character varying) RETURNS real
    LANGUAGE plpgsql
    AS $$
DECLARE
total float;

BEGIN
SELECT sum(contratos.importe_total) into total 
FROM contratos JOIN autos ON contratos.cont_id_auto=autos.id_auto
JOIN modelos ON autos.id_modelo_auto=modelos.id_modelo 
JOIN marcas ON marcas.id_marca=modelos.modelo_id_marca
WHERE marcas.nombre_marca=marc AND modelos.nombre_modelo=model;
if(total ISNULL)THEN
total=0;
END IF;
RETURN total;
END
$$;


ALTER FUNCTION public.calcular_importe_total(model character varying, marc character varying) OWNER TO postgres;

--
-- TOC entry 259 (class 1255 OID 24611)
-- Name: calcular_importe_total_turista(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_importe_total_turista(id_t character varying) RETURNS real
    LANGUAGE plpgsql
    AS $$
DECLARE
tot float;
id_turis int;
BEGIN
SELECT turista.id_tur INTO id_turis FROM turista WHERE turista.pasaporte=id_t;
SELECT sum(contratos.importe_total) INTO tot from contratos where contratos.cont_id_tur=id_turis ;
return tot;
END
$$;


ALTER FUNCTION public.calcular_importe_total_turista(id_t character varying) OWNER TO postgres;

--
-- TOC entry 260 (class 1255 OID 24612)
-- Name: calcular_total_carros_marca_modelo(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION calcular_total_carros_marca_modelo(model character varying, marc character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
contador INT;
id_mar int;
id_m int;
BEGIN
SELECT marcas.id_marca INTO id_mar FROM marcas WHERE marcas.nombre_marca=marc;
SELECT modelos.id_modelo INTO id_m FROM modelos WHERE modelos.nombre_modelo=model AND modelos.modelo_id_marca=id_mar;

select count(autos.id_auto) into contador
FROM autos JOIN modelos ON modelos.id_modelo=autos.id_modelo_auto
JOIN marcas ON marcas.id_marca=modelos.modelo_id_marca
WHERE marcas.id_marca=id_mar AND modelos.id_modelo=id_m;
RETURN contador;
END
$$;


ALTER FUNCTION public.calcular_total_carros_marca_modelo(model character varying, marc character varying) OWNER TO postgres;

--
-- TOC entry 261 (class 1255 OID 24613)
-- Name: cant_carros_manejados_chofer(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cant_carros_manejados_chofer(id_chof integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
tot INTEGER;

BEGIN

SELECT count(contratos.cont_id_chof) into tot
from contratos WHERE contratos.cont_id_chof=id_chof;
return tot;
END
$$;


ALTER FUNCTION public.cant_carros_manejados_chofer(id_chof integer) OWNER TO postgres;

--
-- TOC entry 251 (class 1255 OID 24614)
-- Name: cantidad_de_autos_alquilados_turista(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cantidad_de_autos_alquilados_turista(id_t character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
tot INTEGER;
tur int;
BEGIN
SELECT turista.id_tur INTO tur FROM turista WHERE turista.pasaporte=id_t;
SELECT count(contratos.cont_id_tur) into tot
from contratos WHERE contratos.cont_id_tur=tur;
return tot;
END
$$;


ALTER FUNCTION public.cantidad_de_autos_alquilados_turista(id_t character varying) OWNER TO postgres;

--
-- TOC entry 262 (class 1255 OID 24615)
-- Name: cerrar_contrato(character varying, character varying, date, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cerrar_contrato(chap character varying, pass_t character varying, fi date, fe date) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_a INT;
id_t INT;
id_c INT;
id_fp INT;
id_cont int;
importe money;
BEGIN
SELECT autos.id_auto INTO id_a FROM autos WHERE autos.chapa=chap;
SELECT turista.id_tur INTO id_t FROM turista WHERE turista.pasaporte=pass_t;
SELECT contratos.id_contrato INTO id_cont FROM contratos WHERE contratos.cont_id_auto=id_a AND cont_id_tur=id_t AND fecha_i=fi;
--raise exception 'el id del contrato es %',id_c;
--raise exception 'la fecha entes %',fe;
UPDATE contratos set fecha_entrega=fe
WHERE contratos.id_contrato=id_cont;


SELECT calcular_importe(id_cont) INTO importe ;
--RAISE EXCEPTION 'el importe es %',importe;
--raise EXCEPTION 'el id del contrato es%',id_cont;
UPDATE contratos set importe_total=importe
WHERE contratos.id_contrato=id_cont;

UPDATE autos set id_situacion_auto=(SELECT situaciones.id_sit FROM situaciones WHERE situaciones.tipo_situacion LIKE 'libre') WHERE id_auto=id_a;
END
$$;


ALTER FUNCTION public.cerrar_contrato(chap character varying, pass_t character varying, fi date, fe date) OWNER TO postgres;

--
-- TOC entry 263 (class 1255 OID 24616)
-- Name: check_estado_autos(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION check_estado_autos() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
sit VARCHAR;
BEGIN
SELECT situaciones.tipo_situacion into sit FROM situaciones 
JOIN autos ON situaciones.id_sit=autos.id_situacion_auto
WHERE autos.id_auto=NEW.cont_id_auto;
if(sit!='libre') THEN
raise EXCEPTION 'El auto no se puede alquilar';
ELSE
UPDATE autos set id_situacion_auto=(SELECT situaciones.id_sit FROM situaciones WHERE situaciones.tipo_situacion like '%ocupado%')::INTEGER WHERE autos.id_auto=NEW.cont_id_auto;

END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION public.check_estado_autos() OWNER TO postgres;

--
-- TOC entry 264 (class 1255 OID 24617)
-- Name: check_estado_autos_elim(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION check_estado_autos_elim() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
sit VARCHAR;
id_libre int;
BEGIN
SELECT situaciones.tipo_situacion into sit FROM situaciones 
JOIN autos ON situaciones.id_sit=autos.id_situacion_auto
WHERE autos.id_auto=OLD.cont_id_auto;
SELECT situaciones.id_sit into id_libre from situaciones WHERE situaciones.tipo_situacion LIKE '%libre%';
if(sit='alquilado') THEN
UPDATE autos set id_situacion_auto=id_libre WHERE autos.id_auto=OLD.cont_id_auto;
END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION public.check_estado_autos_elim() OWNER TO postgres;

--
-- TOC entry 265 (class 1255 OID 24618)
-- Name: check_fechas(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION check_fechas() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
if(NEW.fecha_f<NEW.fecha_i) THEN
RAISE EXCEPTION 'La fecha fin no puede ser menor que la fecha de inicio del contrato';
ELSIF(NEW.fecha_entrega NOTNULL) THEN
	IF(NEW.fecha_entrega<NEW.fecha_i)THEN
			RAISE EXCEPTION 'La fecha de entrega no puede ser menor que la fecha de inicio';
	END IF;
END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION public.check_fechas() OWNER TO postgres;

--
-- TOC entry 266 (class 1255 OID 24619)
-- Name: check_importes(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION check_importes() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE 
imp float;
actImp float;
BEGIN
SELECT contratos.importe_total INTO actImp FROM contratos WHERE contratos.id_contrato=NEW.id_contrato;

imp=calcular_importe(NEW.id_contrato);
UPDATE contratos set importe_total=imp
WHERE contratos.id_contrato=NEW.id_contrato;
RETURN NEW;
END
$$;


ALTER FUNCTION public.check_importes() OWNER TO postgres;

--
-- TOC entry 267 (class 1255 OID 24620)
-- Name: comprobar_info_del_usuario(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION comprobar_info_del_usuario() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
if(NEW.usuario ISNULL) THEN
raise EXCEPTION 'El nombre de usuario no puede etar vacio';
ELSIF(NEW.password_usuario ISNULL) THEN
raise EXCEPTION 'La contrasenna no puede estar vacia';
ELSIF(NEW.nombre ISNULL) THEN
raise EXCEPTION 'El nombre no puede estar vacio';
ELSIF(NEW.apellidos ISNULL)THEN
raise EXCEPTION 'Los apellidos no pueden estar vacios';
END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION public.comprobar_info_del_usuario() OWNER TO postgres;

--
-- TOC entry 268 (class 1255 OID 24621)
-- Name: contract_to_traces(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION contract_to_traces() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
INSERT INTO trazas(id_usuario,tabla_modificada,fecha,id_tupla_modificada,valor_antiguo,operacion,direccion_ip) VALUES(OLD.id_usuario,'contratos',now(),OLD.id_contrato,contrato_to_string(OLD.id_contrato),'operacion','address');
RETURN OLD;
END
$$;


ALTER FUNCTION public.contract_to_traces() OWNER TO postgres;

--
-- TOC entry 269 (class 1255 OID 24622)
-- Name: contrato_to_string(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION contrato_to_string(id_contrato integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $_$
DECLARE
resultado VARCHAR;
chapa VARCHAR;
turist VARCHAR;
fechai VARCHAR;
fechaf VARCHAR;
fechae VARCHAR;
chofer VARCHAR;
fp VARCHAR;
import VARCHAR;
BEGIN
SELECT autos.chapa INTO chapa FROM autos JOIN contratos ON contratos.cont_id_auto=autos.id_auto
WHERE contratos.id_contrato=$1;

SELECT turista.pasaporte INTO turist FROM turista JOIN contratos ON contratos.cont_id_tur=turista.id_tur
WHERE contratos.id_contrato=$1;

SELECT contratos.fecha_i::VARCHAR INTO fechai FROM contratos WHERE contratos.id_contrato=$1;


SELECT contratos.fecha_f::VARCHAR INTO fechaf FROM contratos WHERE contratos.id_contrato=$1;

SELECT contratos.fecha_entrega::VARCHAR INTO fechae FROM contratos WHERE contratos.id_contrato=$1;

if(fechae ISNULL)THEN
fechae:='-';
end if;

SELECT choferes.numero_id::VARCHAR INTO chofer FROM contratos JOIN choferes ON contratos.cont_id_chof=choferes.id_chofer
WHERE contratos.id_contrato=$1;
if(chofer ISNULL)THEN
chofer:='no';
end if;
SELECT forma_pago.tipo_pago INTO fp FROM forma_pago JOIN contratos on contratos.cont_id_forma_pago=forma_pago.id_pago
WHERE contratos.id_contrato=$1;

SELECT contratos.importe_total::VARCHAR INTO import FROM contratos WHERE contratos.id_contrato=$1;
 
resultado:=chapa || '/ '|| turist || '/ ' || fechai || '/ ' || fechaf || '/ ' || fechae || '/ ' || chofer || '/ ' || fp || '/ ' || import; 
RETURN resultado;
END
$_$;


ALTER FUNCTION public.contrato_to_string(id_contrato integer) OWNER TO postgres;

--
-- TOC entry 270 (class 1255 OID 24623)
-- Name: dias_prorroga_marca_modelo(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dias_prorroga_marca_modelo(model character varying, marc character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
total INT;
id_mar int;
id_m int;
BEGIN
total=0;
SELECT marcas.id_marca INTO id_mar FROM marcas WHERE marcas.nombre_marca=marc;
SELECT modelos.id_modelo INTO id_m FROM modelos WHERE modelos.nombre_modelo=model AND modelos.modelo_id_marca=id_mar;

SELECT sum(fecha_entrega-fecha_f) into total
from contratos JOIN autos on contratos.cont_id_auto=autos.id_auto
JOIN modelos ON modelos.id_modelo=autos.id_modelo_auto JOIN marcas ON marcas.id_marca=modelos.modelo_id_marca
WHERE marcas.id_marca=id_mar and id_m=modelos.id_modelo and (fecha_entrega-fecha_f)>=0;
RETURN total;
END
$$;


ALTER FUNCTION public.dias_prorroga_marca_modelo(model character varying, marc character varying) OWNER TO postgres;

--
-- TOC entry 271 (class 1255 OID 24624)
-- Name: eliminar_auto(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_auto(character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
DELETE FROM autos WHERE chapa=$1;
END
$_$;


ALTER FUNCTION public.eliminar_auto(character varying) OWNER TO postgres;

--
-- TOC entry 272 (class 1255 OID 24625)
-- Name: eliminar_categoria(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_categoria(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
DELETE FROM categorias WHERE categorias.tipo_categoria=cat;
END
$$;


ALTER FUNCTION public.eliminar_categoria(cat character varying) OWNER TO postgres;

--
-- TOC entry 273 (class 1255 OID 24626)
-- Name: eliminar_chofer(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_chofer(character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
DELETE FROM choferes WHERE choferes.numero_id=$1;
END
$_$;


ALTER FUNCTION public.eliminar_chofer(character varying) OWNER TO postgres;

--
-- TOC entry 274 (class 1255 OID 24627)
-- Name: eliminar_contrato(character varying, character varying, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_contrato(chap character varying, pass_t character varying, fi date) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_cont int;
id_a int;
id_t int;
BEGIN
SELECT autos.id_auto into id_a from autos WHERE autos.chapa=chap;
SELECT turista.id_tur into id_t FROM turista WHERE turista.pasaporte=pass_t;
SELECT contratos.id_contrato INTO id_cont FROM contratos WHERE contratos.cont_id_auto=id_a AND cont_id_tur=id_t AND fecha_i=fi;
DELETE FROM contratos WHERE contratos.id_contrato=id_cont;
END
$$;


ALTER FUNCTION public.eliminar_contrato(chap character varying, pass_t character varying, fi date) OWNER TO postgres;

--
-- TOC entry 275 (class 1255 OID 24628)
-- Name: eliminar_forma_pago(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_forma_pago(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
DELETE FROM forma_pago WHERE forma_pago.tipo_pago=cat;
END
$$;


ALTER FUNCTION public.eliminar_forma_pago(cat character varying) OWNER TO postgres;

--
-- TOC entry 276 (class 1255 OID 24629)
-- Name: eliminar_marca(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_marca(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
DELETE FROM marcas WHERE marcas.nombre_marca=cat;
END
$$;


ALTER FUNCTION public.eliminar_marca(cat character varying) OWNER TO postgres;

--
-- TOC entry 277 (class 1255 OID 24630)
-- Name: eliminar_modelo(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_modelo(model character varying, marc character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_ma INT;
BEGIN
SELECT marcas.id_marca INTO id_ma FROM marcas WHERE marcas.nombre_marca=marc;
DELETE FROM modelos WHERE modelos.nombre_modelo=model AND modelos.modelo_id_marca=id_ma;
END
$$;


ALTER FUNCTION public.eliminar_modelo(model character varying, marc character varying) OWNER TO postgres;

--
-- TOC entry 278 (class 1255 OID 24631)
-- Name: eliminar_pais(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_pais(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
DELETE FROM pais WHERE pais.nombre_pais=cat;
END
$$;


ALTER FUNCTION public.eliminar_pais(cat character varying) OWNER TO postgres;

--
-- TOC entry 279 (class 1255 OID 24632)
-- Name: eliminar_rol(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_rol(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
DELETE FROM rol WHERE rol.tipo_rol=cat;
END
$$;


ALTER FUNCTION public.eliminar_rol(cat character varying) OWNER TO postgres;

--
-- TOC entry 280 (class 1255 OID 24633)
-- Name: eliminar_rol_usuario(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_rol_usuario(tipo character varying, usern character varying) RETURNS void
    LANGUAGE plpgsql COST 200
    AS $$
DECLARE
id_r int;
id_u int;
BEGIN
SELECT rol.id_rol INTO id_r FROM rol WHERE rol.tipo_rol=tipo;
SELECT usuarios.id_user INTO id_u FROM usuarios WHERE userN=usuarios.usuario;
DELETE FROM rol_usuario WHERE rol_id_rol=id_r and usuario_id_user=id_u;
END;
$$;


ALTER FUNCTION public.eliminar_rol_usuario(tipo character varying, usern character varying) OWNER TO postgres;

--
-- TOC entry 281 (class 1255 OID 24634)
-- Name: eliminar_situacion(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_situacion(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
DELETE FROM situaciones WHERE situaciones.tipo_situacion=cat;
END
$$;


ALTER FUNCTION public.eliminar_situacion(cat character varying) OWNER TO postgres;

--
-- TOC entry 282 (class 1255 OID 24635)
-- Name: eliminar_tarifa(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_tarifa(tar_n character varying, tar_e character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
DELETE FROM tarifa WHERE tarifa.tarifa_normal=(tar_n)::money and tarifa.tarifa_especial=(tar_e)::money;
END
$$;


ALTER FUNCTION public.eliminar_tarifa(tar_n character varying, tar_e character varying) OWNER TO postgres;

--
-- TOC entry 283 (class 1255 OID 24636)
-- Name: eliminar_turista(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_turista(character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
DELETE FROM turista WHERE pasaporte=$1;
END
$_$;


ALTER FUNCTION public.eliminar_turista(character varying) OWNER TO postgres;

--
-- TOC entry 284 (class 1255 OID 24637)
-- Name: eliminar_uuario(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION eliminar_uuario(usern character varying, contra character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
DELETE FROM usuarios WHERE usuarios.usuario=userN AND usuarios.password_usuario=contra;
END
$$;


ALTER FUNCTION public.eliminar_uuario(usern character varying, contra character varying) OWNER TO postgres;

--
-- TOC entry 285 (class 1255 OID 24638)
-- Name: esta_alquilado(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION esta_alquilado(id_a integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
aux VARCHAR;
ff date;
BEGIN
SELECT max(contratos.fecha_f) INTO ff FROM contratos JOIN autos 
ON autos.id_auto=contratos.cont_id_auto JOIN situaciones ON autos.id_situacion_auto=situaciones.id_sit
WHERE situaciones.tipo_situacion='ocupado' AND autos.id_auto=id_a
GROUP BY contratos.fecha_f
ORDER BY contratos.fecha_f ASC;

if(ff ISNULL)THEN
aux='NO';
ELSE
aux=(ff)::VARCHAR;
END IF;
RETURN aux;
END
 $$;


ALTER FUNCTION public.esta_alquilado(id_a integer) OWNER TO postgres;

--
-- TOC entry 286 (class 1255 OID 24639)
-- Name: fecha_contrato_valida(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION fecha_contrato_valida() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
act_fecha date;
BEGIN
act_fecha:=now();
IF(NEW.fecha_i>act_fecha)THEN
raise EXCEPTION 'la fecha de inicio del contrato no puede ser distinta a la fecha actual';
END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION public.fecha_contrato_valida() OWNER TO postgres;

--
-- TOC entry 287 (class 1255 OID 24640)
-- Name: hay_chofer(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION hay_chofer(id_cont integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
aux VARCHAR;
id_ch int;
BEGIN
aux='SI';
SELECT contratos.cont_id_chof INTO id_ch from contratos WHERE contratos.id_contrato=id_cont;
if(id_ch ISNULL)THEN
aux='NO';
END IF;
RETURN aux;
END
$$;


ALTER FUNCTION public.hay_chofer(id_cont integer) OWNER TO postgres;

--
-- TOC entry 288 (class 1255 OID 24641)
-- Name: identidad_del_chofer(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION identidad_del_chofer(integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $_$
DECLARE
res VARCHAR:=NULL;
BEGIN
SELECT choferes.numero_id INTO res FROM choferes WHERE choferes.id_chofer=$1;
if(res ISNULL)THEN res:='NO';
END IF;
RETURN res;
END
$_$;


ALTER FUNCTION public.identidad_del_chofer(integer) OWNER TO postgres;

--
-- TOC entry 289 (class 1255 OID 24642)
-- Name: importe_total(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION importe_total() RETURNS real
    LANGUAGE plpgsql
    AS $$
DECLARE
tot float;

BEGIN

SELECT sum(contratos.importe_total) INTO tot from contratos ;
return tot;
END
$$;


ALTER FUNCTION public.importe_total() OWNER TO postgres;

--
-- TOC entry 290 (class 1255 OID 24643)
-- Name: ingreso_anno(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION ingreso_anno(integer) RETURNS real
    LANGUAGE plpgsql
    AS $_$
DECLARE
total float;
BEGIN
SELECT sum(contratos.importe_total) INTO total FROM contratos
WHERE (date_part('YEAR', contratos.fecha_entrega)=$1);
RETURN total;
END
$_$;


ALTER FUNCTION public.ingreso_anno(integer) OWNER TO postgres;

--
-- TOC entry 291 (class 1255 OID 24644)
-- Name: ingreso_mes(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION ingreso_mes(mes integer) RETURNS real
    LANGUAGE plpgsql
    AS $$
DECLARE
tot float;
BEGIN
SELECT sum(importe_total) into tot 
from contratos JOIN autos on contratos.cont_id_auto=autos.id_auto

WHERE date_part('month', fecha_entrega) = mes;
RETURN tot;
END
$$;


ALTER FUNCTION public.ingreso_mes(mes integer) OWNER TO postgres;

--
-- TOC entry 292 (class 1255 OID 24645)
-- Name: insertar_auto(character varying, character varying, character varying, character varying, double precision, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_auto(chap character varying, situacion character varying, modelo character varying, marca character varying, km double precision, col character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_m int;
id_mar int;
id_s int;
BEGIN
SELECT marcas.id_marca INTO id_mar FROM marcas WHERE marcas.nombre_marca=marca;
SELECT modelos.id_modelo INTO id_m FROM modelos WHERE modelos.nombre_modelo=modelo AND modelos.modelo_id_marca=id_mar;
SELECT situaciones.id_sit INTO id_s FROM situaciones WHERE situaciones.tipo_situacion=situacion;
INSERT INTO autos(chapa,id_modelo_auto,id_situacion_auto,color,kilometros) VALUES(chap,id_m,id_s,col,km);
END
$$;


ALTER FUNCTION public.insertar_auto(chap character varying, situacion character varying, modelo character varying, marca character varying, km double precision, col character varying) OWNER TO postgres;

--
-- TOC entry 293 (class 1255 OID 24646)
-- Name: insertar_categoria(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_categoria(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
INSERT INTO categorias(tipo_categoria) VALUES(cat);
END
$$;


ALTER FUNCTION public.insertar_categoria(cat character varying) OWNER TO postgres;

--
-- TOC entry 294 (class 1255 OID 24647)
-- Name: insertar_chofer(character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_chofer(ident character varying, nom character varying, apell character varying, cat character varying, dir character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
cat_cod int;
BEGIN
SELECT categorias.id_cat INTO cat_cod FROM categorias WHERE categorias.tipo_categoria=cat;
INSERT INTO choferes(numero_id,nombre,apellidos,categoria,direccion) VALUES(ident,nom,apell,cat_cod,dir);
END
$$;


ALTER FUNCTION public.insertar_chofer(ident character varying, nom character varying, apell character varying, cat character varying, dir character varying) OWNER TO postgres;

--
-- TOC entry 295 (class 1255 OID 24648)
-- Name: insertar_contrato(character varying, character varying, date, date, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_contrato(chap character varying, pass_t character varying, fi date, ff date, chof character varying, fp character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_a INT;
id_t INT;
id_c INT;
id_fp INT;
id_cont int;
importe float;
BEGIN
SELECT autos.id_auto INTO id_a FROM autos WHERE autos.chapa=chap;
SELECT turista.id_tur INTO id_t FROM turista WHERE turista.pasaporte=pass_t;
SELECT choferes.id_chofer INTO id_c FROM choferes WHERE choferes.numero_id=chof;
SELECT forma_pago.id_pago INTO id_fp FROM forma_pago WHERE forma_pago.tipo_pago=fp;
INSERT INTO contratos(cont_id_auto,cont_id_tur,fecha_i,fecha_f,cont_id_chof,cont_id_forma_pago) VALUES(id_a,id_t,fi,ff,id_c,id_fp);

SELECT contratos.id_contrato INTO id_cont FROM contratos WHERE contratos.cont_id_auto=id_a AND cont_id_tur=id_t AND fecha_i=fi;
importe=calcular_importe(id_cont);
UPDATE contratos set importe_total=importe
WHERE contratos.id_contrato=id_cont;
END
$$;


ALTER FUNCTION public.insertar_contrato(chap character varying, pass_t character varying, fi date, ff date, chof character varying, fp character varying) OWNER TO postgres;

--
-- TOC entry 296 (class 1255 OID 24649)
-- Name: insertar_contrato(character varying, character varying, date, date, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_contrato(chap character varying, pass_t character varying, fi date, ff date, chof character varying, fp character varying, id_u integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_a INT;
id_t INT;
id_c INT;
id_fp INT;
id_cont int;
importe money;
BEGIN
SELECT autos.id_auto INTO id_a FROM autos WHERE autos.chapa=chap;
SELECT turista.id_tur INTO id_t FROM turista WHERE turista.pasaporte=pass_t;
SELECT choferes.id_chofer INTO id_c FROM choferes WHERE choferes.numero_id=chof;
SELECT forma_pago.id_pago INTO id_fp FROM forma_pago WHERE forma_pago.tipo_pago=fp;
INSERT INTO contratos(cont_id_auto,cont_id_tur,fecha_i,fecha_f,cont_id_chof,cont_id_forma_pago,id_usuario) VALUES(id_a,id_t,fi,ff,id_c,id_fp,id_u);

SELECT contratos.id_contrato INTO id_cont FROM contratos WHERE contratos.cont_id_auto=id_a AND cont_id_tur=id_t AND fecha_i=fi;
importe=calcular_importe(id_cont);
UPDATE contratos set importe_total=importe
WHERE contratos.id_contrato=id_cont;
END
$$;


ALTER FUNCTION public.insertar_contrato(chap character varying, pass_t character varying, fi date, ff date, chof character varying, fp character varying, id_u integer) OWNER TO postgres;

--
-- TOC entry 297 (class 1255 OID 24650)
-- Name: insertar_forma_pago(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_forma_pago(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
INSERT INTO forma_pago(tipo_pago) VALUES(cat);
END
$$;


ALTER FUNCTION public.insertar_forma_pago(cat character varying) OWNER TO postgres;

--
-- TOC entry 298 (class 1255 OID 24651)
-- Name: insertar_marca(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_marca(character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
INSERT INTO marcas(nombre_marca) VALUES($1);
END
$_$;


ALTER FUNCTION public.insertar_marca(character varying) OWNER TO postgres;

--
-- TOC entry 299 (class 1255 OID 24652)
-- Name: insertar_modelo(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_modelo(model character varying, marc character varying, tar_n character varying, tar_e character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_ma INT;
id_t int;
BEGIN
SELECT tarifa.id_tarifa into id_t FROM tarifa WHERE tarifa_normal=(tar_n)::money and tarifa_eSpecial=(tar_e)::money;
SELECT marcas.id_marca INTO id_ma FROM marcas WHERE marcas.nombre_marca=marc;
INSERT INTO modelos(nombre_modelo,modelo_id_marca,modelo_id_tar) VALUES(model,id_ma,id_t);
END
$$;


ALTER FUNCTION public.insertar_modelo(model character varying, marc character varying, tar_n character varying, tar_e character varying) OWNER TO postgres;

--
-- TOC entry 300 (class 1255 OID 24653)
-- Name: insertar_pais(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_pais(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
INSERT INTO pais(nombre_pais) VALUES(cat);
END
$$;


ALTER FUNCTION public.insertar_pais(cat character varying) OWNER TO postgres;

--
-- TOC entry 301 (class 1255 OID 24654)
-- Name: insertar_rol(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_rol(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
INSERT INTO rol(tipo_rol) VALUES(cat);
END
$$;


ALTER FUNCTION public.insertar_rol(cat character varying) OWNER TO postgres;

--
-- TOC entry 302 (class 1255 OID 24655)
-- Name: insertar_rol_usuario(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_rol_usuario(tipo character varying, usern character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_r int;
id_u int;
BEGIN
SELECT rol.id_rol INTO id_r FROM rol WHERE rol.tipo_rol=tipo;
SELECT usuarios.id_user INTO id_u FROM usuarios WHERE userN=usuarios.usuario;
INSERT INTO rol_usuario(rol_id_rol,usuario_id_user) VALUES(id_r,id_u);
END
$$;


ALTER FUNCTION public.insertar_rol_usuario(tipo character varying, usern character varying) OWNER TO postgres;

--
-- TOC entry 303 (class 1255 OID 24656)
-- Name: insertar_situacion(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_situacion(cat character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
INSERT INTO situaciones(tipo_situacion) VALUES(cat);
END
$$;


ALTER FUNCTION public.insertar_situacion(cat character varying) OWNER TO postgres;

--
-- TOC entry 304 (class 1255 OID 24657)
-- Name: insertar_tarifa(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_tarifa(tarn character varying, tare character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_m int;
id_ma int;
BEGIN

INSERT INTO tarifa(tarifa_normal,tarifa_especial) VALUES((tarn)::money,(tare)::money);
END
$$;


ALTER FUNCTION public.insertar_tarifa(tarn character varying, tare character varying) OWNER TO postgres;

--
-- TOC entry 305 (class 1255 OID 24658)
-- Name: insertar_tarifa(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_tarifa(model character varying, marc character varying, tarn character varying, tare character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_m int;
id_ma int;
BEGIN
SELECT marcas.id_marca INTO id_ma FROM marcas WHERE marcas.nombre_marca=marc;
SELECT modelos.id_modelo INTO id_m FROM modelos WHERE modelos.nombre_modelo=model AND modelos.modelo_id_marca=id_ma;
INSERT INTO tarifas(id_model,id_marc,tarifa_normal,tarifa_especial) VALUES(id_m,id_ma,(tarn)::money,(tare)::money);
END
$$;


ALTER FUNCTION public.insertar_tarifa(model character varying, marc character varying, tarn character varying, tare character varying) OWNER TO postgres;

--
-- TOC entry 306 (class 1255 OID 24659)
-- Name: insertar_turista(character varying, character varying, character varying, integer, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_turista(id_t character varying, nom character varying, apell character varying, eda integer, sex character varying, tel character varying, pa character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE 
id_p INT;
BEGIN
SELECT pais.id_pais INTO id_p FROM pais WHERE nombre_pais=pa;
INSERT INTO turista(pasaporte,nombre,apellidos,edad,sexo,telefono,tur_id_pais) VALUES(id_t,nom,apell,eda,sex,tel,id_p);
END
$$;


ALTER FUNCTION public.insertar_turista(id_t character varying, nom character varying, apell character varying, eda integer, sex character varying, tel character varying, pa character varying) OWNER TO postgres;

--
-- TOC entry 307 (class 1255 OID 24660)
-- Name: insertar_usuario(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION insertar_usuario(usern character varying, contra character varying, nom character varying, apell character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
INSERT INTO usuarios(usuario,password_usuario,nombre, apellidos) VALUES(userN,contra,nom,apell);
END
$$;


ALTER FUNCTION public.insertar_usuario(usern character varying, contra character varying, nom character varying, apell character varying) OWNER TO postgres;

--
-- TOC entry 308 (class 1255 OID 24661)
-- Name: login_function(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION login_function(usr character varying, pass character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
resultado BOOLEAN:=FALSE;
id_r int;
id_u int;
pas VARCHAR;
BEGIN
SELECT usuarios.id_user INTO id_u 
FROM usuarios 
WHERE usuarios.usuario=usr AND usuarios.password_usuario=pass;
if(id_u NOTNULL)THEN
SELECT usuarios.password_usuario INTO pas FROM usuarios WHERE usuarios.id_user=id_u;
if(pas=pass)THEN
  resultado:= TRUE;
END IF;
END IF;
RETURN resultado;
END
$$;


ALTER FUNCTION public.login_function(usr character varying, pass character varying) OWNER TO postgres;

--
-- TOC entry 309 (class 1255 OID 24662)
-- Name: login_function(character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION login_function(trol character varying, usr character varying, pass character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
resultado BOOLEAN:=FALSE;
id_r int;
id_u int;
pas VARCHAR;
BEGIN
SELECT rol.id_rol INTO id_r FROM rol JOIN rol_usuario ON rol_usuario.rol_id_rol=rol.id_rol WHERE rol.tipo_rol=trol;

SELECT rol_usuario.usuario_id_user INTO id_u 
FROM rol_usuario JOIN usuarios ON usuarios.id_user=rol_usuario.usuario_id_user
WHERE rol_usuario.rol_id_rol=id_r AND usuarios.usuario=usr AND usuarios.password_usuario=pass;
if(id_r NOTNULL AND id_u NOTNULL)THEN
SELECT usuarios.password_usuario INTO pas FROM usuarios WHERE usuarios.id_user=id_u;
if(pas=pass)THEN
  resultado:= TRUE;
END IF;
END IF;
RETURN resultado;
END
$$;


ALTER FUNCTION public.login_function(trol character varying, usr character varying, pass character varying) OWNER TO postgres;

--
-- TOC entry 310 (class 1255 OID 24663)
-- Name: mayor_de_edad(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION mayor_de_edad() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
if(NEW.edad<18)THEN
raise EXCEPTION 'Debe ser mayor de 18 annos para alquilar';
END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION public.mayor_de_edad() OWNER TO postgres;

--
-- TOC entry 311 (class 1255 OID 24664)
-- Name: modificar_auto(character varying, character varying, character varying, character varying, double precision, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_auto(chap character varying, situacion character varying, modelo character varying, marca character varying, km double precision, col character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_m int;
id_mar int;
id_s int;
BEGIN
SELECT marcas.id_marca INTO id_mar FROM marcas WHERE marcas.nombre_marca=marca;
SELECT modelos.id_modelo INTO id_m FROM modelos WHERE modelos.nombre_modelo=modelo AND modelos.modelo_id_marca=id_mar;
SELECT situaciones.id_sit INTO id_s FROM situaciones WHERE situaciones.tipo_situacion=situacion;
UPDATE autos set id_modelo_auto=id_m,id_situacion_auto=id_s,color=col,kilometros=km 
WHERE autos.chapa=chap;
END
$$;


ALTER FUNCTION public.modificar_auto(chap character varying, situacion character varying, modelo character varying, marca character varying, km double precision, col character varying) OWNER TO postgres;

--
-- TOC entry 312 (class 1255 OID 24665)
-- Name: modificar_auto(character varying, character varying, character varying, character varying, double precision, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_auto(chap character varying, situacion character varying, modelo character varying, marca character varying, km double precision, col character varying, id_a integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_m int;
id_mar int;
id_s int;
BEGIN
SELECT marcas.id_marca INTO id_mar FROM marcas WHERE marcas.nombre_marca=marca;
SELECT modelos.id_modelo INTO id_m FROM modelos WHERE modelos.nombre_modelo=modelo AND modelos.modelo_id_marca=id_mar;
SELECT situaciones.id_sit INTO id_s FROM situaciones WHERE situaciones.tipo_situacion=situacion;

UPDATE autos 
set id_modelo_auto=id_m,
		id_situacion_auto=id_s,
		color=col,
		kilometros=km , 
		chapa=chap
WHERE autos.id_auto=id_a;
END
$$;


ALTER FUNCTION public.modificar_auto(chap character varying, situacion character varying, modelo character varying, marca character varying, km double precision, col character varying, id_a integer) OWNER TO postgres;

--
-- TOC entry 313 (class 1255 OID 24666)
-- Name: modificar_chofer(character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_chofer(ident character varying, nom character varying, apell character varying, cat character varying, dir character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
cat_cod VARCHAR;
BEGIN
SELECT categorias.tipo_categoria INTO cat_cod FROM categorias WHERE categorias.tipo_categoria=cat;
UPDATE choferes set nombre=nom,apellidos=apell,categoria=cat_cod,direccion=dir WHERE numero_id=ident;
END
$$;


ALTER FUNCTION public.modificar_chofer(ident character varying, nom character varying, apell character varying, cat character varying, dir character varying) OWNER TO postgres;

--
-- TOC entry 314 (class 1255 OID 24667)
-- Name: modificar_chofer(character varying, character varying, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_chofer(ident character varying, nom character varying, apell character varying, cat character varying, dir character varying, id_cho integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
cat_cod int;
BEGIN
SELECT categorias.id_cat INTO cat_cod FROM categorias WHERE categorias.tipo_categoria=cat;
UPDATE choferes 
set nombre=nom,
		apellidos=apell,
		categoria=cat_cod,
		direccion=dir,
		numero_id=ident
WHERE id_chofer=id_cho;
END
$$;


ALTER FUNCTION public.modificar_chofer(ident character varying, nom character varying, apell character varying, cat character varying, dir character varying, id_cho integer) OWNER TO postgres;

--
-- TOC entry 315 (class 1255 OID 24668)
-- Name: modificar_contrato(character varying, character varying, date, date, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_contrato(chap character varying, pass_t character varying, fi date, ff date, chof character varying, fp character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_a INT;
id_t INT;
id_c INT;
id_fp INT;
id_cont int;
importe money;
BEGIN
SELECT autos.id_auto INTO id_a FROM autos WHERE autos.chapa=chap;
SELECT turista.id_tur INTO id_t FROM turista WHERE turista.pasaporte=pass_t;
SELECT choferes.id_chofer INTO id_c FROM choferes WHERE choferes.numero_id=chof;
SELECT contratos.id_contrato INTO id_cont FROM contratos WHERE contratos.cont_id_auto=id_a AND cont_id_tur=id_t AND fecha_i=fi;
SELECT forma_pago.id_pago INTO id_fp FROM forma_pago WHERE forma_pago.tipo_pago=fp;

UPDATE contratos set fecha_f=ff,cont_id_chof=id_c,cont_id_forma_pago=id_fp
WHERE contratos.id_contrato=id_cont;

importe=calcular_importe(id_cont);
UPDATE contratos set importe_total=importe
WHERE contratos.id_contrato=id_cont;
END
$$;


ALTER FUNCTION public.modificar_contrato(chap character varying, pass_t character varying, fi date, ff date, chof character varying, fp character varying) OWNER TO postgres;

--
-- TOC entry 316 (class 1255 OID 24669)
-- Name: modificar_contrato(character varying, character varying, date, date, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_contrato(chap character varying, pass_t character varying, fi date, ff date, chof character varying, fp character varying, id_u integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_a INT;
id_t INT;
id_c INT;
id_fp INT;
id_cont int;
importe money;
BEGIN
SELECT autos.id_auto INTO id_a FROM autos WHERE autos.chapa=chap;
SELECT turista.id_tur INTO id_t FROM turista WHERE turista.pasaporte=pass_t;
SELECT choferes.id_chofer INTO id_c FROM choferes WHERE choferes.numero_id=chof;
SELECT contratos.id_contrato INTO id_cont FROM contratos WHERE contratos.cont_id_auto=id_a AND cont_id_tur=id_t AND fecha_i=fi;
SELECT forma_pago.id_pago INTO id_fp FROM forma_pago WHERE forma_pago.tipo_pago=fp;

UPDATE contratos set fecha_f=ff,cont_id_chof=id_c,cont_id_forma_pago=id_fp,id_usuario=id_u
WHERE contratos.id_contrato=id_cont;

importe=calcular_importe(id_cont);
UPDATE contratos set importe_total=importe
WHERE contratos.id_contrato=id_cont;
END
$$;


ALTER FUNCTION public.modificar_contrato(chap character varying, pass_t character varying, fi date, ff date, chof character varying, fp character varying, id_u integer) OWNER TO postgres;

--
-- TOC entry 317 (class 1255 OID 24670)
-- Name: modificar_modelo(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_modelo(model character varying, marc character varying, tar_n character varying, tar_e character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
id_ma INT;
id_t int;
BEGIN
SELECT tarifa.id_tarifa into id_t FROM tarifa WHERE tarifa_normal=(tar_n)::money and tarifa_especial=(tar_e)::money;
SELECT marcas.id_marca INTO id_ma FROM marcas WHERE marcas.nombre_marca=marc;
UPDATE modelos set modelo_id_tar=id_t WHERE modelos.nombre_modelo=model and modelo_id_marca=id_ma;
END
$$;


ALTER FUNCTION public.modificar_modelo(model character varying, marc character varying, tar_n character varying, tar_e character varying) OWNER TO postgres;

--
-- TOC entry 318 (class 1255 OID 24671)
-- Name: modificar_turista(character varying, character varying, character varying, integer, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_turista(id_t character varying, nom character varying, apell character varying, eda integer, sex character varying, tel character varying, pa character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE 
id_p INT;
BEGIN
SELECT pais.id_pais INTO id_p FROM pais WHERE nombre_pais=pa;
UPDATE turista set nombre=nom,apellidos=apell,edad=eda,sexo=sex,telefono=tel,tur_id_pais=id_p WHERE pasaporte=id_t;
END
$$;


ALTER FUNCTION public.modificar_turista(id_t character varying, nom character varying, apell character varying, eda integer, sex character varying, tel character varying, pa character varying) OWNER TO postgres;

--
-- TOC entry 319 (class 1255 OID 24672)
-- Name: modificar_turista(character varying, character varying, character varying, integer, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_turista(id_t character varying, nom character varying, apell character varying, eda integer, sex character varying, tel character varying, pa character varying, id_turist integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE 
id_p INT;
BEGIN
SELECT pais.id_pais INTO id_p FROM pais WHERE nombre_pais=pa;
UPDATE turista set nombre=nom,apellidos=apell,edad=eda,sexo=sex,telefono=tel,tur_id_pais=id_p,pasaporte=id_t
 WHERE turista.id_tur=id_turist ;
END
$$;


ALTER FUNCTION public.modificar_turista(id_t character varying, nom character varying, apell character varying, eda integer, sex character varying, tel character varying, pa character varying, id_turist integer) OWNER TO postgres;

--
-- TOC entry 320 (class 1255 OID 24673)
-- Name: modificar_usuario(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION modificar_usuario(usern character varying, contra character varying, nom character varying, apell character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
UPDATE usuarios set password_usuario=contra,nombre=nom, apellidos=apell
WHERE usuarios.usuario=userN;
END
$$;


ALTER FUNCTION public.modificar_usuario(usern character varying, contra character varying, nom character varying, apell character varying) OWNER TO postgres;

--
-- TOC entry 321 (class 1255 OID 24674)
-- Name: rev_estado_autos_en_elim(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rev_estado_autos_en_elim() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
UPDATE autos set id_situacion_auto=6 
WHERE id_auto=(SELECT contratos.cont_id_auto FROM contratos JOIN autos ON contratos.cont_id_auto=autos.id_auto
JOIN turista ON contratos.cont_id_tur=turista.id_tur
WHERE contratos.cont_id_tur=OLD.id_tur);
RETURN OLD;
END
$$;


ALTER FUNCTION public.rev_estado_autos_en_elim() OWNER TO postgres;

--
-- TOC entry 322 (class 1255 OID 24675)
-- Name: tarifa_positiva(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tarifa_positiva() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
IF((NEW.tarifa_normal)<(1)::money or (NEW.tarifa_especial)<(1)::money)THEN
raise EXCEPTION 'las tarifas deben ser mayores que cero';
end if;
RETURN NEW;
END
$$;


ALTER FUNCTION public.tarifa_positiva() OWNER TO postgres;

--
-- TOC entry 323 (class 1255 OID 24676)
-- Name: total_dias_alquilado_marca_modelo(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION total_dias_alquilado_marca_modelo(model character varying, marc character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
total INT;
aux int;
id_mar int;
id_m int;
BEGIN
total=0;
SELECT marcas.id_marca INTO id_mar FROM marcas WHERE marcas.nombre_marca=marc;
SELECT modelos.id_modelo INTO id_m FROM modelos WHERE modelos.nombre_modelo=model AND modelos.modelo_id_marca=id_mar;

SELECT sum(fecha_entrega-fecha_i) into total
from contratos JOIN autos on contratos.cont_id_auto=autos.id_auto
JOIN modelos ON modelos.id_modelo=autos.id_modelo_auto JOIN marcas ON marcas.id_marca=modelos.modelo_id_marca
WHERE marcas.id_marca=id_mar and id_m=modelos.id_modelo;
RETURN total; 
END
$$;


ALTER FUNCTION public.total_dias_alquilado_marca_modelo(model character varying, marc character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- TOC entry 170 (class 1259 OID 24677)
-- Name: pais; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pais (
    id_pais integer NOT NULL,
    nombre_pais character varying NOT NULL
);


ALTER TABLE public.pais OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 24683)
-- Name: Pais_id_pais_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Pais_id_pais_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Pais_id_pais_seq" OWNER TO postgres;

--
-- TOC entry 2321 (class 0 OID 0)
-- Dependencies: 171
-- Name: Pais_id_pais_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Pais_id_pais_seq" OWNED BY pais.id_pais;


--
-- TOC entry 172 (class 1259 OID 24685)
-- Name: autos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE autos (
    id_auto integer NOT NULL,
    chapa character varying NOT NULL,
    id_modelo_auto integer NOT NULL,
    id_situacion_auto integer NOT NULL,
    color character varying NOT NULL,
    kilometros real NOT NULL,
    CONSTRAINT kilom_positivo CHECK ((kilometros >= (0)::double precision))
);


ALTER TABLE public.autos OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 24692)
-- Name: autos_id_auto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE autos_id_auto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.autos_id_auto_seq OWNER TO postgres;

--
-- TOC entry 2322 (class 0 OID 0)
-- Dependencies: 173
-- Name: autos_id_auto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE autos_id_auto_seq OWNED BY autos.id_auto;


--
-- TOC entry 174 (class 1259 OID 24694)
-- Name: categorias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE categorias (
    id_cat integer NOT NULL,
    tipo_categoria character varying NOT NULL
);


ALTER TABLE public.categorias OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 24700)
-- Name: categorias_id_cat_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE categorias_id_cat_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categorias_id_cat_seq OWNER TO postgres;

--
-- TOC entry 2323 (class 0 OID 0)
-- Dependencies: 175
-- Name: categorias_id_cat_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE categorias_id_cat_seq OWNED BY categorias.id_cat;


--
-- TOC entry 176 (class 1259 OID 24702)
-- Name: choferes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE choferes (
    id_chofer integer NOT NULL,
    numero_id character varying NOT NULL,
    nombre character varying NOT NULL,
    apellidos character varying NOT NULL,
    categoria integer NOT NULL,
    direccion character varying NOT NULL
);


ALTER TABLE public.choferes OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 24708)
-- Name: choferes_id_chofer_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE choferes_id_chofer_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.choferes_id_chofer_seq OWNER TO postgres;

--
-- TOC entry 2324 (class 0 OID 0)
-- Dependencies: 177
-- Name: choferes_id_chofer_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE choferes_id_chofer_seq OWNED BY choferes.id_chofer;


--
-- TOC entry 178 (class 1259 OID 24710)
-- Name: contratos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE contratos (
    cont_id_auto integer NOT NULL,
    cont_id_tur integer NOT NULL,
    fecha_i date NOT NULL,
    fecha_f date NOT NULL,
    fecha_entrega date,
    cont_id_chof integer,
    cont_id_forma_pago integer NOT NULL,
    importe_total double precision,
    id_contrato integer NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE public.contratos OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 24713)
-- Name: marcas; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE marcas (
    id_marca integer NOT NULL,
    nombre_marca character varying NOT NULL
);


ALTER TABLE public.marcas OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 24719)
-- Name: modelos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE modelos (
    id_modelo integer NOT NULL,
    nombre_modelo character varying(1024) NOT NULL,
    modelo_id_marca integer NOT NULL,
    modelo_id_tar integer NOT NULL
);


ALTER TABLE public.modelos OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 24725)
-- Name: turista; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE turista (
    id_tur integer NOT NULL,
    pasaporte character varying NOT NULL,
    nombre character varying NOT NULL,
    apellidos character varying NOT NULL,
    edad integer NOT NULL,
    sexo character varying NOT NULL,
    telefono character varying NOT NULL,
    tur_id_pais integer NOT NULL,
    CONSTRAINT "mayor de edad" CHECK ((edad > 18))
);


ALTER TABLE public.turista OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 24732)
-- Name: contrato_pais; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW contrato_pais AS
 SELECT DISTINCT pais.nombre_pais, 
    marcas.nombre_marca, 
    modelos.nombre_modelo, 
    total_dias_alquilado_marca_modelo(modelos.nombre_modelo, marcas.nombre_marca) AS dias_alquilado, 
    dias_prorroga_marca_modelo(modelos.nombre_modelo, marcas.nombre_marca) AS dias_prorroga, 
    (calcular_importe_efectivo(modelos.nombre_modelo, marcas.nombre_marca))::character varying AS importe_efectivo, 
    (calcular_importe_total(modelos.nombre_modelo, marcas.nombre_marca))::character varying AS calcular_importe_marca, 
    row_number() OVER (ORDER BY pais.nombre_pais) AS id_view
   FROM (((((contratos
   JOIN turista ON ((contratos.cont_id_tur = turista.id_tur)))
   JOIN pais ON ((turista.tur_id_pais = pais.id_pais)))
   JOIN autos ON ((contratos.cont_id_auto = autos.id_auto)))
   JOIN modelos ON ((modelos.id_modelo = autos.id_modelo_auto)))
   JOIN marcas ON ((modelos.modelo_id_marca = marcas.id_marca)));


ALTER TABLE public.contrato_pais OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 24737)
-- Name: contrato_por_modelo_marca; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW contrato_por_modelo_marca AS
 SELECT DISTINCT marcas.nombre_marca, 
    modelos.nombre_modelo, 
    calcular_total_carros_marca_modelo(modelos.nombre_modelo, marcas.nombre_marca) AS total_carros_mar_mod, 
    total_dias_alquilado_marca_modelo(modelos.nombre_modelo, marcas.nombre_marca) AS total_dias_alquilado, 
    (calcular_importe_tarjeta(modelos.nombre_modelo, marcas.nombre_marca))::character varying AS importe_tarjeta, 
    (calcular_importe_cheque(modelos.nombre_modelo, marcas.nombre_marca))::character varying AS importe_cheque, 
    (calcular_importe_efectivo(modelos.nombre_modelo, marcas.nombre_marca))::character varying AS importe_efectivo, 
    (calcular_importe_marca(marcas.nombre_marca))::character varying AS calcular_importe_marca, 
    (importe_total())::character varying AS importe_total, 
    row_number() OVER (ORDER BY marcas.nombre_marca) AS id_view
   FROM (((autos
   JOIN contratos ON ((contratos.cont_id_auto = autos.id_auto)))
   JOIN modelos ON ((autos.id_modelo_auto = modelos.id_modelo)))
   JOIN marcas ON ((modelos.modelo_id_marca = marcas.id_marca)));


ALTER TABLE public.contrato_por_modelo_marca OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 24742)
-- Name: contratos_id_contrato_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE contratos_id_contrato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contratos_id_contrato_seq OWNER TO postgres;

--
-- TOC entry 2325 (class 0 OID 0)
-- Dependencies: 184
-- Name: contratos_id_contrato_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE contratos_id_contrato_seq OWNED BY contratos.id_contrato;


--
-- TOC entry 185 (class 1259 OID 24744)
-- Name: forma_pago; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE forma_pago (
    id_pago integer NOT NULL,
    tipo_pago character varying NOT NULL
);


ALTER TABLE public.forma_pago OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 24750)
-- Name: forma_pago_id_pago_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE forma_pago_id_pago_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forma_pago_id_pago_seq OWNER TO postgres;

--
-- TOC entry 2326 (class 0 OID 0)
-- Dependencies: 186
-- Name: forma_pago_id_pago_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE forma_pago_id_pago_seq OWNED BY forma_pago.id_pago;


--
-- TOC entry 187 (class 1259 OID 24752)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 24754)
-- Name: listado_autos; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW listado_autos AS
 SELECT autos.chapa, 
    marcas.nombre_marca, 
    modelos.nombre_modelo, 
    autos.color, 
    autos.kilometros, 
    autos.id_auto AS id_view
   FROM ((autos
   JOIN modelos ON ((autos.id_modelo_auto = modelos.id_modelo)))
   JOIN marcas ON ((modelos.modelo_id_marca = marcas.id_marca)));


ALTER TABLE public.listado_autos OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 24758)
-- Name: listado_chofer; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW listado_chofer AS
 SELECT choferes.numero_id, 
    choferes.nombre, 
    choferes.apellidos, 
    choferes.direccion, 
    categorias.tipo_categoria AS categoria, 
    cant_carros_manejados_chofer(choferes.id_chofer) AS cant_carros_manejados_chofer, 
    choferes.id_chofer AS id_view
   FROM (choferes
   JOIN categorias ON ((choferes.categoria = categorias.id_cat)));


ALTER TABLE public.listado_chofer OWNER TO postgres;

SET default_with_oids = false;

--
-- TOC entry 190 (class 1259 OID 24762)
-- Name: listado_choferes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE listado_choferes (
    id_view integer NOT NULL,
    apellidos character varying(255),
    cant_carros_manejados_chofer integer,
    categoria character varying(255),
    direccion character varying(255),
    nombre character varying(255),
    numero_id character varying(255)
);


ALTER TABLE public.listado_choferes OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 24768)
-- Name: listado_contratos; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW listado_contratos AS
 SELECT DISTINCT turista.nombre, 
    modelos.nombre_modelo, 
    marcas.nombre_marca, 
    autos.chapa, 
    forma_pago.tipo_pago, 
    contratos.fecha_i, 
    contratos.fecha_f, 
    calcular_dias_prorroga_contrato(contratos.id_contrato) AS calcular_dias_prorroga_contrato, 
    hay_chofer(contratos.id_contrato) AS hay_chofer, 
    (contratos.importe_total)::character varying AS importe_total, 
    contratos.id_contrato AS id_view
   FROM (((((contratos
   JOIN autos ON ((contratos.cont_id_auto = autos.id_auto)))
   JOIN modelos ON ((autos.id_modelo_auto = modelos.id_modelo)))
   JOIN marcas ON ((modelos.modelo_id_marca = marcas.id_marca)))
   JOIN turista ON ((contratos.cont_id_tur = turista.id_tur)))
   JOIN forma_pago ON ((contratos.cont_id_forma_pago = forma_pago.id_pago)));


ALTER TABLE public.listado_contratos OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 24773)
-- Name: listado_incumplidores; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW listado_incumplidores AS
 SELECT turista.nombre, 
    turista.apellidos, 
    contratos.fecha_f, 
    contratos.fecha_entrega, 
    row_number() OVER (ORDER BY turista.nombre) AS id_view
   FROM (contratos
   JOIN turista ON ((contratos.cont_id_tur = turista.id_tur)))
  WHERE (contratos.fecha_entrega > contratos.fecha_f);


ALTER TABLE public.listado_incumplidores OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 24777)
-- Name: listado_ingresos; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW listado_ingresos AS
 SELECT DISTINCT (date_part('YEAR'::text, contratos.fecha_entrega))::integer AS anno, 
    (ingreso_anno((date_part('YEAR'::text, contratos.fecha_entrega))::integer))::character varying AS ingreso_anno, 
    to_char((contratos.fecha_entrega)::timestamp with time zone, 'MONTH'::text) AS mes, 
    (ingreso_mes((date_part('MONTH'::text, contratos.fecha_entrega))::integer))::character varying AS ingreso_mes, 
    row_number() OVER (ORDER BY (date_part('YEAR'::text, contratos.fecha_entrega))::integer) AS id_view
   FROM ((contratos
   JOIN autos ON ((contratos.cont_id_auto = autos.id_auto)))
   JOIN turista ON ((contratos.cont_id_tur = turista.id_tur)))
  GROUP BY contratos.fecha_entrega
 HAVING (ingreso_anno((date_part('YEAR'::text, contratos.fecha_entrega))::integer) IS NOT NULL)
  ORDER BY (date_part('YEAR'::text, contratos.fecha_entrega))::integer;


ALTER TABLE public.listado_ingresos OWNER TO postgres;

SET default_with_oids = true;

--
-- TOC entry 194 (class 1259 OID 24782)
-- Name: situaciones; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE situaciones (
    id_sit integer NOT NULL,
    tipo_situacion character varying NOT NULL
);


ALTER TABLE public.situaciones OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 24788)
-- Name: listado_situaciones; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW listado_situaciones AS
 SELECT DISTINCT autos.chapa, 
    marcas.nombre_marca, 
    situaciones.tipo_situacion, 
    esta_alquilado(autos.id_auto) AS esta_alquilado, 
    row_number() OVER (ORDER BY autos.chapa) AS id_view
   FROM (((autos
   LEFT JOIN situaciones ON ((autos.id_situacion_auto = situaciones.id_sit)))
   JOIN modelos ON ((autos.id_modelo_auto = modelos.id_modelo)))
   JOIN marcas ON ((marcas.id_marca = modelos.modelo_id_marca)));


ALTER TABLE public.listado_situaciones OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 24793)
-- Name: marcas_id_marca_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE marcas_id_marca_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.marcas_id_marca_seq OWNER TO postgres;

--
-- TOC entry 2327 (class 0 OID 0)
-- Dependencies: 196
-- Name: marcas_id_marca_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE marcas_id_marca_seq OWNED BY marcas.id_marca;


--
-- TOC entry 197 (class 1259 OID 24795)
-- Name: modelos_id_modelo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE modelos_id_modelo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.modelos_id_modelo_seq OWNER TO postgres;

--
-- TOC entry 2328 (class 0 OID 0)
-- Dependencies: 197
-- Name: modelos_id_modelo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE modelos_id_modelo_seq OWNED BY modelos.id_modelo;


--
-- TOC entry 198 (class 1259 OID 24797)
-- Name: rol; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rol (
    id_rol integer NOT NULL,
    tipo_rol character varying NOT NULL
);


ALTER TABLE public.rol OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 24803)
-- Name: rol_id_rol_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rol_id_rol_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rol_id_rol_seq OWNER TO postgres;

--
-- TOC entry 2329 (class 0 OID 0)
-- Dependencies: 199
-- Name: rol_id_rol_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rol_id_rol_seq OWNED BY rol.id_rol;


--
-- TOC entry 200 (class 1259 OID 24805)
-- Name: rol_usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rol_usuario (
    rol_id_rol integer NOT NULL,
    usuario_id_user integer NOT NULL,
    id_rol_usuario integer NOT NULL
);


ALTER TABLE public.rol_usuario OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 24808)
-- Name: rol_usuario_id_rol_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rol_usuario_id_rol_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rol_usuario_id_rol_usuario_seq OWNER TO postgres;

--
-- TOC entry 2330 (class 0 OID 0)
-- Dependencies: 201
-- Name: rol_usuario_id_rol_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rol_usuario_id_rol_usuario_seq OWNED BY rol_usuario.id_rol_usuario;


--
-- TOC entry 202 (class 1259 OID 24810)
-- Name: situaciones_id_sit_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE situaciones_id_sit_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.situaciones_id_sit_seq OWNER TO postgres;

--
-- TOC entry 2331 (class 0 OID 0)
-- Dependencies: 202
-- Name: situaciones_id_sit_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE situaciones_id_sit_seq OWNED BY situaciones.id_sit;


SET default_with_oids = false;

--
-- TOC entry 203 (class 1259 OID 24812)
-- Name: tarifa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tarifa (
    id_tarifa integer NOT NULL,
    tarifa_normal double precision NOT NULL,
    tarifa_especial double precision NOT NULL
);


ALTER TABLE public.tarifa OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 24815)
-- Name: tarifa_id_tarifa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tarifa_id_tarifa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tarifa_id_tarifa_seq OWNER TO postgres;

--
-- TOC entry 2332 (class 0 OID 0)
-- Dependencies: 204
-- Name: tarifa_id_tarifa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tarifa_id_tarifa_seq OWNED BY tarifa.id_tarifa;


--
-- TOC entry 205 (class 1259 OID 24817)
-- Name: trazas; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE trazas (
    id_traza integer NOT NULL,
    id_usuario integer NOT NULL,
    operacion character varying NOT NULL,
    tabla_modificada character varying NOT NULL,
    fecha date NOT NULL,
    direccion_ip character varying NOT NULL,
    id_tupla_modificada integer NOT NULL,
    hora character varying(255)
);


ALTER TABLE public.trazas OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 24823)
-- Name: trazas_id_traza_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE trazas_id_traza_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.trazas_id_traza_seq OWNER TO postgres;

--
-- TOC entry 2333 (class 0 OID 0)
-- Dependencies: 206
-- Name: trazas_id_traza_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE trazas_id_traza_seq OWNED BY trazas.id_traza;


--
-- TOC entry 207 (class 1259 OID 24825)
-- Name: turista_id_tur_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE turista_id_tur_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.turista_id_tur_seq OWNER TO postgres;

--
-- TOC entry 2334 (class 0 OID 0)
-- Dependencies: 207
-- Name: turista_id_tur_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE turista_id_tur_seq OWNED BY turista.id_tur;


--
-- TOC entry 208 (class 1259 OID 24827)
-- Name: turistas_pais; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW turistas_pais AS
 SELECT pais.nombre_pais, 
    turista.pasaporte, 
    turista.nombre, 
    turista.apellidos, 
    cantidad_de_autos_alquilados_turista(turista.pasaporte) AS autos_alquilados, 
    (calcular_importe_total_turista(turista.pasaporte))::character varying AS importe_total, 
    row_number() OVER (ORDER BY pais.nombre_pais) AS id_view
   FROM (pais
   JOIN turista ON ((turista.tur_id_pais = pais.id_pais)));


ALTER TABLE public.turistas_pais OWNER TO postgres;

SET default_with_oids = true;

--
-- TOC entry 209 (class 1259 OID 24831)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuarios (
    id_user integer NOT NULL,
    usuario character varying NOT NULL,
    password_usuario character varying NOT NULL,
    nombre character varying NOT NULL,
    apellidos character varying NOT NULL
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 24837)
-- Name: usuarios_id_user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuarios_id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_id_user_seq OWNER TO postgres;

--
-- TOC entry 2335 (class 0 OID 0)
-- Dependencies: 210
-- Name: usuarios_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE usuarios_id_user_seq OWNED BY usuarios.id_user;


--
-- TOC entry 2063 (class 2604 OID 24839)
-- Name: id_auto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY autos ALTER COLUMN id_auto SET DEFAULT nextval('autos_id_auto_seq'::regclass);


--
-- TOC entry 2065 (class 2604 OID 24840)
-- Name: id_cat; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categorias ALTER COLUMN id_cat SET DEFAULT nextval('categorias_id_cat_seq'::regclass);


--
-- TOC entry 2066 (class 2604 OID 24841)
-- Name: id_chofer; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY choferes ALTER COLUMN id_chofer SET DEFAULT nextval('choferes_id_chofer_seq'::regclass);


--
-- TOC entry 2067 (class 2604 OID 24842)
-- Name: id_contrato; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contratos ALTER COLUMN id_contrato SET DEFAULT nextval('contratos_id_contrato_seq'::regclass);


--
-- TOC entry 2072 (class 2604 OID 24843)
-- Name: id_pago; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY forma_pago ALTER COLUMN id_pago SET DEFAULT nextval('forma_pago_id_pago_seq'::regclass);


--
-- TOC entry 2068 (class 2604 OID 24844)
-- Name: id_marca; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY marcas ALTER COLUMN id_marca SET DEFAULT nextval('marcas_id_marca_seq'::regclass);


--
-- TOC entry 2069 (class 2604 OID 24845)
-- Name: id_modelo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modelos ALTER COLUMN id_modelo SET DEFAULT nextval('modelos_id_modelo_seq'::regclass);


--
-- TOC entry 2062 (class 2604 OID 24846)
-- Name: id_pais; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pais ALTER COLUMN id_pais SET DEFAULT nextval('"Pais_id_pais_seq"'::regclass);


--
-- TOC entry 2074 (class 2604 OID 24847)
-- Name: id_rol; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rol ALTER COLUMN id_rol SET DEFAULT nextval('rol_id_rol_seq'::regclass);


--
-- TOC entry 2075 (class 2604 OID 24848)
-- Name: id_rol_usuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rol_usuario ALTER COLUMN id_rol_usuario SET DEFAULT nextval('rol_usuario_id_rol_usuario_seq'::regclass);


--
-- TOC entry 2073 (class 2604 OID 24849)
-- Name: id_sit; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY situaciones ALTER COLUMN id_sit SET DEFAULT nextval('situaciones_id_sit_seq'::regclass);


--
-- TOC entry 2076 (class 2604 OID 24850)
-- Name: id_tarifa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tarifa ALTER COLUMN id_tarifa SET DEFAULT nextval('tarifa_id_tarifa_seq'::regclass);


--
-- TOC entry 2077 (class 2604 OID 24851)
-- Name: id_traza; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY trazas ALTER COLUMN id_traza SET DEFAULT nextval('trazas_id_traza_seq'::regclass);


--
-- TOC entry 2070 (class 2604 OID 24852)
-- Name: id_tur; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY turista ALTER COLUMN id_tur SET DEFAULT nextval('turista_id_tur_seq'::regclass);


--
-- TOC entry 2078 (class 2604 OID 24853)
-- Name: id_user; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuarios ALTER COLUMN id_user SET DEFAULT nextval('usuarios_id_user_seq'::regclass);


--
-- TOC entry 2336 (class 0 OID 0)
-- Dependencies: 171
-- Name: Pais_id_pais_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"Pais_id_pais_seq"', 97, true);


--
-- TOC entry 2283 (class 0 OID 24685)
-- Dependencies: 172
-- Data for Name: autos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY autos (id_auto, chapa, id_modelo_auto, id_situacion_auto, color, kilometros) FROM stdin;
3	t567890	3	6	negro	0
6	t354233	10	7	rojo	9999
5	t543232	6	6	azul	300
2	t453232	2	6	blanco	90
7	t999555	12	6	gris	90
4	t645322	4	5	rojo	5
\.


--
-- TOC entry 2337 (class 0 OID 0)
-- Dependencies: 173
-- Name: autos_id_auto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('autos_id_auto_seq', 7, true);


--
-- TOC entry 2285 (class 0 OID 24694)
-- Dependencies: 174
-- Data for Name: categorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY categorias (id_cat, tipo_categoria) FROM stdin;
5	transportador
6	novel
7	profesional
8	dc comics
\.


--
-- TOC entry 2338 (class 0 OID 0)
-- Dependencies: 175
-- Name: categorias_id_cat_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('categorias_id_cat_seq', 29, true);


--
-- TOC entry 2287 (class 0 OID 24702)
-- Dependencies: 176
-- Data for Name: choferes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY choferes (id_chofer, numero_id, nombre, apellidos, categoria, direccion) FROM stdin;
3	89070689543	Jhon	Snow	7	USA
1	95684321433	Robert	De Niro	5	usa
4	15934444559	Sébastien 	Loeb	7	francia
5	78322133233	Michèle 	Mouton	7	francia
6	34543663564	Émile 	Levassor	7	francia
7	96060609462	Luisito	Suarez	5	la habana, cuba
8	34754358743	Robert	 Kubica	7	polonia
\.


--
-- TOC entry 2339 (class 0 OID 0)
-- Dependencies: 177
-- Name: choferes_id_chofer_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('choferes_id_chofer_seq', 8, true);


--
-- TOC entry 2289 (class 0 OID 24710)
-- Dependencies: 178
-- Data for Name: contratos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY contratos (cont_id_auto, cont_id_tur, fecha_i, fecha_f, fecha_entrega, cont_id_chof, cont_id_forma_pago, importe_total, id_contrato, id_usuario) FROM stdin;
5	13	2017-06-01	2017-06-17	2017-06-23	\N	4	1160	5	15
2	12	2017-06-01	2017-06-30	2017-07-01	3	5	150.5	4	15
4	19	2017-06-15	2017-07-09	2017-07-30	\N	6	17700	7	16
7	14	2017-06-08	2017-06-23	2017-06-19	\N	4	880	8	15
4	15	2017-06-14	2017-06-23	\N	\N	4	2700	9	15
\.


--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 184
-- Name: contratos_id_contrato_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('contratos_id_contrato_seq', 9, true);


--
-- TOC entry 2294 (class 0 OID 24744)
-- Dependencies: 185
-- Data for Name: forma_pago; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY forma_pago (id_pago, tipo_pago) FROM stdin;
4	efectivo
5	tarjeta de credito
6	cheque
\.


--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 186
-- Name: forma_pago_id_pago_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('forma_pago_id_pago_seq', 6, true);


--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 187
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- TOC entry 2297 (class 0 OID 24762)
-- Dependencies: 190
-- Data for Name: listado_choferes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY listado_choferes (id_view, apellidos, cant_carros_manejados_chofer, categoria, direccion, nombre, numero_id) FROM stdin;
\.


--
-- TOC entry 2290 (class 0 OID 24713)
-- Dependencies: 179
-- Data for Name: marcas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY marcas (id_marca, nombre_marca) FROM stdin;
12	geely
13	audi
14	rols royce
16	ferrari
17	lamborgini
18	bat-movil
19	ford
20	chevrolet
21	kia
22	hyunday
23	toyota
24	byq
25	seat
26	gm
27	lada
28	mosckovish
29	volga
30	peugeot
31	mercedez bentz
36	moseratti
\.


--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 196
-- Name: marcas_id_marca_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('marcas_id_marca_seq', 36, true);


--
-- TOC entry 2291 (class 0 OID 24719)
-- Dependencies: 180
-- Data for Name: modelos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY modelos (id_modelo, nombre_modelo, modelo_id_marca, modelo_id_tar) FROM stdin;
2	ck	12	5
3	emgrand	12	2
4	diablo	17	1
5	a4	13	3
6	rio	21	2
7	h100	22	5
8	mustang	19	1
9	1600	27	5
10	106	30	2
11	206	30	4
12	407	30	3
13	24	29	5
14	phantom	14	1
15	lr8	13	1
16	corvette	20	1
17	sonata	22	2
18	accent	22	2
19	a6	13	1
20	ibiza	25	2
\.


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 197
-- Name: modelos_id_modelo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('modelos_id_modelo_seq', 20, true);


--
-- TOC entry 2281 (class 0 OID 24677)
-- Dependencies: 170
-- Data for Name: pais; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pais (id_pais, nombre_pais) FROM stdin;
54	cuba
55	japon
56	ecuador
57	disneylandia
58	dc comics 
59	marvel's universe
60	china
61	india
62	colombia
63	brazil
64	usa
65	canada
66	noruega
67	alemania
68	rusia
69	argentina
70	bolivia
71	venezuela
72	francia
73	españa
74	hungria
75	finlandia
76	holanda
77	mexico
78	suiza
79	vietnam
80	checoslovakia
81	honduras
82	uruguay
83	nicaragua
84	australia
85	uk
97	otro infierno7
\.


--
-- TOC entry 2301 (class 0 OID 24797)
-- Dependencies: 198
-- Data for Name: rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rol (id_rol, tipo_rol) FROM stdin;
4	Administrador
5	Gerente
6	Dependiente
\.


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 199
-- Name: rol_id_rol_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rol_id_rol_seq', 6, true);


--
-- TOC entry 2303 (class 0 OID 24805)
-- Dependencies: 200
-- Data for Name: rol_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rol_usuario (rol_id_rol, usuario_id_user, id_rol_usuario) FROM stdin;
4	14	1
4	15	2
5	15	3
5	16	9
6	27	10
6	16	13
\.


--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 201
-- Name: rol_usuario_id_rol_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rol_usuario_id_rol_usuario_seq', 13, true);


--
-- TOC entry 2298 (class 0 OID 24782)
-- Dependencies: 194
-- Data for Name: situaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY situaciones (id_sit, tipo_situacion) FROM stdin;
5	ocupado
6	libre
7	taller
8	fuera de servicio
\.


--
-- TOC entry 2347 (class 0 OID 0)
-- Dependencies: 202
-- Name: situaciones_id_sit_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('situaciones_id_sit_seq', 8, true);


--
-- TOC entry 2306 (class 0 OID 24812)
-- Dependencies: 203
-- Data for Name: tarifa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tarifa (id_tarifa, tarifa_normal, tarifa_especial) FROM stdin;
1	300	500
2	50	60
3	80	90
4	70	10
5	5	5.5
\.


--
-- TOC entry 2348 (class 0 OID 0)
-- Dependencies: 204
-- Name: tarifa_id_tarifa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tarifa_id_tarifa_seq', 12, true);


--
-- TOC entry 2308 (class 0 OID 24817)
-- Dependencies: 205
-- Data for Name: trazas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY trazas (id_traza, id_usuario, operacion, tabla_modificada, fecha, direccion_ip, id_tupla_modificada, hora) FROM stdin;
3	15	CREATE	Categorias	2017-06-13	127.0.0.1	24	\N
4	15	CREATE	Categorias	2017-06-13	127.0.0.1	29	\N
5	15	CREATE	Marcas	2017-06-13	127.0.0.1	35	\N
6	15	CREATE	Marcas	2017-06-13	127.0.0.1	36	\N
7	15	UPDATE	Categorias	2017-06-13	127.0.0.1	29	\N
8	15	UPDATE	Categorias	2017-06-14	127.0.0.1	29	\N
9	14	DELETE	RolUsuario	2017-06-14	127.0.0.1	6	\N
10	14	DELETE	RolUsuario	2017-06-14	127.0.0.1	4	\N
11	14	CREATE	RolUsuario	2017-06-14	127.0.0.1	9	\N
12	14	DELETE	RolUsuario	2017-06-14	127.0.0.1	7	\N
13	14	DELETE	RolUsuario	2017-06-14	127.0.0.1	8	\N
14	14	DELETE	Usuarios	2017-06-14	127.0.0.1	26	\N
15	14	CREATE	Usuarios	2017-06-14	127.0.0.1	27	\N
16	14	CREATE	RolUsuario	2017-06-14	127.0.0.1	10	\N
17	14	UPDATE	Usuarios	2017-06-14	127.0.0.1	27	\N
18	15	DELETE	Categorias	2017-06-15	127.0.0.1	29	\N
19	15	DELETE	Categorias	2017-06-15	127.0.0.1	24	\N
20	15	CREATE	Tarifa	2017-06-16	127.0.0.1	2	\N
21	15	CREATE	Tarifa	2017-06-16	127.0.0.1	3	\N
22	15	CREATE	Tarifa	2017-06-16	127.0.0.1	4	\N
23	15	CREATE	Tarifa	2017-06-16	127.0.0.1	5	\N
24	15	CREATE	RolUsuario	2017-06-17	127.0.0.1	13	\N
30	14	DELETE	RolUsuario	2017-06-17	127.0.0.1	5	\N
31	14	DELETE	Usuarios	2017-06-17	127.0.0.1	24	\N
32	15	CREATE	Modelos	2017-06-17	127.0.0.1	2	\N
33	15	CREATE	Modelos	2017-06-17	127.0.0.1	3	\N
34	15	CREATE	Modelos	2017-06-17	127.0.0.1	4	\N
35	15	CREATE	Modelos	2017-06-17	127.0.0.1	5	\N
36	15	CREATE	Modelos	2017-06-17	127.0.0.1	6	\N
37	15	CREATE	Modelos	2017-06-17	127.0.0.1	7	\N
38	15	CREATE	Modelos	2017-06-17	127.0.0.1	8	\N
39	15	CREATE	Modelos	2017-06-17	127.0.0.1	9	\N
40	15	CREATE	Modelos	2017-06-17	127.0.0.1	10	\N
41	15	CREATE	Modelos	2017-06-17	127.0.0.1	11	\N
42	15	CREATE	Modelos	2017-06-17	127.0.0.1	12	\N
43	15	CREATE	Modelos	2017-06-17	127.0.0.1	13	\N
44	15	CREATE	Modelos	2017-06-17	127.0.0.1	14	\N
45	15	CREATE	Modelos	2017-06-17	127.0.0.1	15	\N
46	15	CREATE	Modelos	2017-06-17	127.0.0.1	16	\N
47	15	CREATE	Modelos	2017-06-17	127.0.0.1	17	\N
48	15	CREATE	Modelos	2017-06-17	127.0.0.1	18	\N
49	15	CREATE	Modelos	2017-06-17	127.0.0.1	19	\N
50	15	CREATE	Modelos	2017-06-17	127.0.0.1	20	\N
51	15	CREATE	Choferes	2017-06-17	127.0.0.1	4	\N
52	15	CREATE	Choferes	2017-06-17	127.0.0.1	5	\N
53	15	CREATE	Choferes	2017-06-17	127.0.0.1	6	\N
54	15	CREATE	Choferes	2017-06-17	127.0.0.1	7	\N
55	15	CREATE	Choferes	2017-06-17	127.0.0.1	8	\N
56	15	CREATE	Autos	2017-06-17	127.0.0.1	2	\N
57	15	CREATE	Autos	2017-06-17	127.0.0.1	3	\N
58	15	CREATE	Autos	2017-06-17	127.0.0.1	4	\N
59	15	CREATE	Autos	2017-06-17	127.0.0.1	5	\N
60	15	CREATE	Autos	2017-06-17	127.0.0.1	6	\N
61	15	CREATE	Contratos	2017-06-17	127.0.0.1	5	\N
62	15	UPDATE	Contratos	2017-06-17	127.0.0.1	5	\N
63	15	UPDATE	Contratos	2017-06-17	127.0.0.1	5	\N
64	15	CREATE	Autos	2017-06-18	127.0.0.1	7	\N
65	15	CREATE	Contratos	2017-06-18	127.0.0.1	8	\N
66	15	UPDATE	Contratos	2017-06-18	127.0.0.1	8	\N
67	15	UPDATE	Contratos	2017-06-18	127.0.0.1	8	\N
68	15	CREATE	Contratos	2017-06-18	127.0.0.1	9	\N
69	15	UPDATE	Usuarios	2017-07-24	127.0.0.1	15	1:05:17
70	15	DELETE	Turista	2017-07-24	127.0.0.1	23	1:06:03
71	15	DELETE	Turista	2017-07-24	127.0.0.1	22	1:06:12
\.


--
-- TOC entry 2349 (class 0 OID 0)
-- Dependencies: 206
-- Name: trazas_id_traza_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('trazas_id_traza_seq', 71, true);


--
-- TOC entry 2292 (class 0 OID 24725)
-- Dependencies: 181
-- Data for Name: turista; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY turista (id_tur, pasaporte, nombre, apellidos, edad, sexo, telefono, tur_id_pais) FROM stdin;
12	96060609462	Luisito	Suarez	21	masculino	58242171	54
13	8954354355	Robert	Downey Jr.	51	masculino	453453443	64
14	254359834	Scarlett 	Johannson	32	femenino	94534590	64
15	49853495	Robert 	De Niro Jr	73	masculino	5345425345	64
16	534589345	Liam	Neeson	75	masculino	43545	64
17	498534589	James	McAvoy	30	masculino	4534534	85
19	4385439534	Jhonny	Deep	53	masculino	43543535	64
20	43h5435	Amber	Heard	30	femenino	4354385934	64
18	j34r34hwf	Keira	Knigthley	32	femenino	845345349	85
\.


--
-- TOC entry 2350 (class 0 OID 0)
-- Dependencies: 207
-- Name: turista_id_tur_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('turista_id_tur_seq', 25, true);


--
-- TOC entry 2311 (class 0 OID 24831)
-- Dependencies: 209
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuarios (id_user, usuario, password_usuario, nombre, apellidos) FROM stdin;
14	admin	9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0	DefaultAdm	default
16	day	9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0	dayle	avellan
25	usuario	9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0	usuario	usuario
27	pepe	9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0	pepito	pepito
15	luisito	9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0	luisito	suarez
\.


--
-- TOC entry 2351 (class 0 OID 0)
-- Dependencies: 210
-- Name: usuarios_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuarios_id_user_seq', 27, true);


--
-- TOC entry 2080 (class 2606 OID 24981)
-- Name: Pais_id_pais_nombre_pais_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT "Pais_id_pais_nombre_pais_key" UNIQUE (nombre_pais);


--
-- TOC entry 2082 (class 2606 OID 24983)
-- Name: Pais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT "Pais_pkey" PRIMARY KEY (id_pais);


--
-- TOC entry 2084 (class 2606 OID 24985)
-- Name: autos_chapa_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY autos
    ADD CONSTRAINT autos_chapa_key UNIQUE (chapa);


--
-- TOC entry 2087 (class 2606 OID 24987)
-- Name: autos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY autos
    ADD CONSTRAINT autos_pkey PRIMARY KEY (id_auto);


--
-- TOC entry 2089 (class 2606 OID 24989)
-- Name: categorias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categorias
    ADD CONSTRAINT categorias_pkey PRIMARY KEY (id_cat);


--
-- TOC entry 2091 (class 2606 OID 24991)
-- Name: categorias_tipo_categoria_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categorias
    ADD CONSTRAINT categorias_tipo_categoria_key UNIQUE (tipo_categoria);


--
-- TOC entry 2093 (class 2606 OID 24993)
-- Name: choferes_numero_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY choferes
    ADD CONSTRAINT choferes_numero_id_key UNIQUE (numero_id);


--
-- TOC entry 2095 (class 2606 OID 24995)
-- Name: choferes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY choferes
    ADD CONSTRAINT choferes_pkey PRIMARY KEY (id_chofer);


--
-- TOC entry 2097 (class 2606 OID 24997)
-- Name: contratos_cont_id_auto_cont_id_tur_fecha_i_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY contratos
    ADD CONSTRAINT contratos_cont_id_auto_cont_id_tur_fecha_i_key UNIQUE (cont_id_auto, cont_id_tur, fecha_i);


--
-- TOC entry 2100 (class 2606 OID 24999)
-- Name: contratos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY contratos
    ADD CONSTRAINT contratos_pkey PRIMARY KEY (id_contrato);


--
-- TOC entry 2114 (class 2606 OID 25001)
-- Name: forma_pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY forma_pago
    ADD CONSTRAINT forma_pago_pkey PRIMARY KEY (id_pago);


--
-- TOC entry 2116 (class 2606 OID 25003)
-- Name: forma_pago_tipo_pago_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY forma_pago
    ADD CONSTRAINT forma_pago_tipo_pago_key UNIQUE (tipo_pago);


--
-- TOC entry 2118 (class 2606 OID 25005)
-- Name: listado_choferes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY listado_choferes
    ADD CONSTRAINT listado_choferes_pkey PRIMARY KEY (id_view);


--
-- TOC entry 2102 (class 2606 OID 25007)
-- Name: marcas_nombre_marca_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marcas
    ADD CONSTRAINT marcas_nombre_marca_key UNIQUE (nombre_marca);


--
-- TOC entry 2104 (class 2606 OID 25009)
-- Name: marcas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marcas
    ADD CONSTRAINT marcas_pkey PRIMARY KEY (id_marca);


--
-- TOC entry 2106 (class 2606 OID 25011)
-- Name: modelos_nombre_modelo_modelo_id_marca_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY modelos
    ADD CONSTRAINT modelos_nombre_modelo_modelo_id_marca_key UNIQUE (nombre_modelo, modelo_id_marca);


--
-- TOC entry 2108 (class 2606 OID 25013)
-- Name: modelos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY modelos
    ADD CONSTRAINT modelos_pkey PRIMARY KEY (id_modelo);


--
-- TOC entry 2124 (class 2606 OID 25015)
-- Name: rol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id_rol);


--
-- TOC entry 2126 (class 2606 OID 25017)
-- Name: rol_tipo_rol_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rol
    ADD CONSTRAINT rol_tipo_rol_key UNIQUE (tipo_rol);


--
-- TOC entry 2128 (class 2606 OID 25019)
-- Name: rol_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rol_usuario
    ADD CONSTRAINT rol_usuario_pkey PRIMARY KEY (id_rol_usuario);


--
-- TOC entry 2130 (class 2606 OID 25021)
-- Name: rol_usuario_rol_id_rol_usuario_id_user_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rol_usuario
    ADD CONSTRAINT rol_usuario_rol_id_rol_usuario_id_user_key UNIQUE (rol_id_rol, usuario_id_user);


--
-- TOC entry 2120 (class 2606 OID 25023)
-- Name: situaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY situaciones
    ADD CONSTRAINT situaciones_pkey PRIMARY KEY (id_sit);


--
-- TOC entry 2122 (class 2606 OID 25025)
-- Name: situaciones_tipo_situacion_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY situaciones
    ADD CONSTRAINT situaciones_tipo_situacion_key UNIQUE (tipo_situacion);


--
-- TOC entry 2132 (class 2606 OID 25027)
-- Name: tarifa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tarifa
    ADD CONSTRAINT tarifa_pkey PRIMARY KEY (id_tarifa);


--
-- TOC entry 2134 (class 2606 OID 25029)
-- Name: tarifa_tarifa_normal_tarifa_especial_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tarifa
    ADD CONSTRAINT tarifa_tarifa_normal_tarifa_especial_key UNIQUE (tarifa_normal, tarifa_especial);


--
-- TOC entry 2136 (class 2606 OID 25031)
-- Name: trazas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY trazas
    ADD CONSTRAINT trazas_pkey PRIMARY KEY (id_traza);


--
-- TOC entry 2110 (class 2606 OID 25033)
-- Name: turista_pasaporte_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY turista
    ADD CONSTRAINT turista_pasaporte_key UNIQUE (pasaporte);


--
-- TOC entry 2112 (class 2606 OID 25035)
-- Name: turista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY turista
    ADD CONSTRAINT turista_pkey PRIMARY KEY (id_tur);


--
-- TOC entry 2138 (class 2606 OID 25037)
-- Name: usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id_user);


--
-- TOC entry 2140 (class 2606 OID 25039)
-- Name: usuarios_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_usuario_key UNIQUE (usuario);


--
-- TOC entry 2085 (class 1259 OID 25040)
-- Name: autos_id_auto_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX autos_id_auto_idx ON autos USING btree (id_auto);


--
-- TOC entry 2098 (class 1259 OID 25041)
-- Name: contratos_id_contrato_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX contratos_id_contrato_idx ON contratos USING btree (id_contrato);


--
-- TOC entry 2155 (class 2620 OID 25042)
-- Name: cerrar_cont; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER cerrar_cont AFTER UPDATE OF fecha_entrega ON contratos FOR EACH ROW EXECUTE PROCEDURE actualizar_situacion();


--
-- TOC entry 2162 (class 2620 OID 25043)
-- Name: check_edad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_edad BEFORE INSERT OR UPDATE ON turista FOR EACH ROW EXECUTE PROCEDURE mayor_de_edad();


--
-- TOC entry 2164 (class 2620 OID 25044)
-- Name: check_usuario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_usuario BEFORE INSERT OR UPDATE ON usuarios FOR EACH ROW EXECUTE PROCEDURE comprobar_info_del_usuario();


--
-- TOC entry 2156 (class 2620 OID 25045)
-- Name: comprobar_alquilar_mismo_turista; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER comprobar_alquilar_mismo_turista BEFORE INSERT ON contratos FOR EACH ROW EXECUTE PROCEDURE alquilar_dos_carros_el_mismo_dia();


--
-- TOC entry 2157 (class 2620 OID 25046)
-- Name: comprobar_est; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER comprobar_est AFTER DELETE ON contratos FOR EACH ROW EXECUTE PROCEDURE check_estado_autos_elim();


--
-- TOC entry 2158 (class 2620 OID 25047)
-- Name: comprobar_fechas; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER comprobar_fechas AFTER INSERT OR UPDATE OF fecha_i, fecha_f, fecha_entrega ON contratos FOR EACH ROW EXECUTE PROCEDURE check_fechas();


--
-- TOC entry 2159 (class 2620 OID 25048)
-- Name: comprobar_se_puede_alquilar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER comprobar_se_puede_alquilar BEFORE INSERT ON contratos FOR EACH ROW EXECUTE PROCEDURE check_estado_autos();


--
-- TOC entry 2163 (class 2620 OID 25049)
-- Name: rev_est_aut_elim_tur; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER rev_est_aut_elim_tur AFTER DELETE ON turista FOR EACH ROW EXECUTE PROCEDURE rev_estado_autos_en_elim();


--
-- TOC entry 2160 (class 2620 OID 25050)
-- Name: rev_import2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER rev_import2 AFTER INSERT ON contratos FOR EACH ROW EXECUTE PROCEDURE check_importes();


--
-- TOC entry 2161 (class 2620 OID 25051)
-- Name: revisar_importes; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER revisar_importes AFTER INSERT OR UPDATE OF fecha_entrega ON contratos FOR EACH ROW EXECUTE PROCEDURE check_importes();


--
-- TOC entry 2141 (class 2606 OID 25052)
-- Name: autos_id_modelo_auto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY autos
    ADD CONSTRAINT autos_id_modelo_auto_fkey FOREIGN KEY (id_modelo_auto) REFERENCES modelos(id_modelo) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2142 (class 2606 OID 25057)
-- Name: autos_id_situacion_auto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY autos
    ADD CONSTRAINT autos_id_situacion_auto_fkey FOREIGN KEY (id_situacion_auto) REFERENCES situaciones(id_sit) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2143 (class 2606 OID 25062)
-- Name: choferes_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY choferes
    ADD CONSTRAINT choferes_categoria_fkey FOREIGN KEY (categoria) REFERENCES categorias(id_cat) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2144 (class 2606 OID 25067)
-- Name: contratos_cont_id_auto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contratos
    ADD CONSTRAINT contratos_cont_id_auto_fkey FOREIGN KEY (cont_id_auto) REFERENCES autos(id_auto) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2145 (class 2606 OID 25072)
-- Name: contratos_cont_id_chof_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contratos
    ADD CONSTRAINT contratos_cont_id_chof_fkey FOREIGN KEY (cont_id_chof) REFERENCES choferes(id_chofer) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2146 (class 2606 OID 25077)
-- Name: contratos_cont_id_forma_pago_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contratos
    ADD CONSTRAINT contratos_cont_id_forma_pago_fkey FOREIGN KEY (cont_id_forma_pago) REFERENCES forma_pago(id_pago) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2147 (class 2606 OID 25082)
-- Name: contratos_cont_id_tur_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contratos
    ADD CONSTRAINT contratos_cont_id_tur_fkey FOREIGN KEY (cont_id_tur) REFERENCES turista(id_tur) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2148 (class 2606 OID 25087)
-- Name: contratos_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contratos
    ADD CONSTRAINT contratos_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES usuarios(id_user) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2149 (class 2606 OID 25092)
-- Name: modelos_modelo_id_marca_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modelos
    ADD CONSTRAINT modelos_modelo_id_marca_fkey FOREIGN KEY (modelo_id_marca) REFERENCES marcas(id_marca) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2150 (class 2606 OID 25097)
-- Name: modelos_modelo_id_tar_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modelos
    ADD CONSTRAINT modelos_modelo_id_tar_fkey FOREIGN KEY (modelo_id_tar) REFERENCES tarifa(id_tarifa) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2152 (class 2606 OID 25102)
-- Name: rol_usuario_rol_id_rol_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rol_usuario
    ADD CONSTRAINT rol_usuario_rol_id_rol_fkey FOREIGN KEY (rol_id_rol) REFERENCES rol(id_rol) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2153 (class 2606 OID 25107)
-- Name: rol_usuario_usuario_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rol_usuario
    ADD CONSTRAINT rol_usuario_usuario_id_user_fkey FOREIGN KEY (usuario_id_user) REFERENCES usuarios(id_user) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2154 (class 2606 OID 25112)
-- Name: trazas_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY trazas
    ADD CONSTRAINT trazas_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES usuarios(id_user) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2151 (class 2606 OID 25117)
-- Name: turista_tur_id_pais_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY turista
    ADD CONSTRAINT turista_tur_id_pais_fkey FOREIGN KEY (tur_id_pais) REFERENCES pais(id_pais) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2319 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-07-27 02:21:36

--
-- PostgreSQL database dump complete
--

