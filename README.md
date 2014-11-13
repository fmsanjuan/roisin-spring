Configuración del proyecto para la conexión con la base de datos usando Tomcat

- Añadir lo siguiente en el fichero context.xml de Tomcat:



- Añadir lo siguiente en la etiqueta Context del fichero web.xml de Tomcat:

<Resource name="jdbc/roisin" auth="Container" type="javax.sql.DataSource"
              username="root" password="root"
              url="jdbc:mysql://localhost:3306/roisin"
              driverClassName="com.mysql.jdbc.Driver"
              initialSize="5" maxWait="5000"
              maxActive="120" maxIdle="5"
              validationQuery="select 1"
              poolPreparedStatements="true"/>
              
Se debe inficar la url de conexión a la base de datos, usuario y contraseña.

Para el correcto funcionamiento ha sido necesario realizar lo siguiente en los ficheros que se indican a continuación:

data.xml

<bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
		<property name="jndiName" value="java:comp/env/jdbc/roisin" />
	</bean>
	
persistence.xml

Añadir a persistence-unit:

<non-jta-data-source>java:comp/env/jdbc/roisin</non-jta-data-source>