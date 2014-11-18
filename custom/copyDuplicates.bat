@ECHO ON
REM
REM Copy duplicate files - Generated by CreateInstallDuplicatesScripts
REM

REM Copy jars from konakart to konakartadmin

COPY %1\webapps\konakart\WEB-INF\lib\mysql-connector-java-5.1.23-bin.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\postgresql-9.1-901.jdbc4.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\ojdbc6.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\db2jcc.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\db2jcc_license_cu.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jtds-1.2.5.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\activation.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\antlr-2.7.5.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\avalon-framework-api-4.3.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\avalon-logkit-2.1.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\axis.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-beanutils-1.8.0.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-collections-3.2.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-configuration-1.7.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-dbcp-1.4.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-digester-2.0.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-discovery-0.2.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-fileupload-1.2.2.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-io-2.0.1.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-lang-2.4.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-lang3-3.1.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-logging-1.1.1.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-pool-1.3.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-validator-1.3.1.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\validation-api-1.0.0.GA.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jakarta-oro.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jaxrpc.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jcs-1.3.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jdbc-2.0.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jdom-1.0.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jndi-1.2.1.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_custom_utils.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_fedex.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_ordertotal*.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_payment*.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_shipping*.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_torque-4.0.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_utils.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_village-4.0.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\konakart_authorizenet.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\log4j-1.2.12.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\logkit-1.0.1.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\mail.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\oro-2.0.8.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\saaj.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\stratum-1.0-b3.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\velocity-1.6.2.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\velocity-tools-generic-1.3.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\werken-xpath-0.9.4.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\wsdl4j-1.5.1.jar %1\webapps\konakartadmin\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\xml-apis-2.0.2.jar %1\webapps\konakartadmin\WEB-INF\lib\

REM Copy jars from konakart to birtviewer

COPY %1\webapps\konakart\WEB-INF\lib\mysql-connector-java-5.1.23-bin.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\postgresql-9.1-901.jdbc4.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\ojdbc6.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\db2jcc.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\db2jcc_license_cu.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jtds-1.2.5.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\activation.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\axis.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-collections-3.2.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-configuration-1.7.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-discovery-0.2.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-lang3-3.1.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\commons-logging-1.1.1.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\jaxrpc.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\log4j-1.2.12.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\mail.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\saaj.jar %1\webapps\birtviewer\WEB-INF\lib\
COPY %1\webapps\konakart\WEB-INF\lib\wsdl4j-1.5.1.jar %1\webapps\birtviewer\WEB-INF\lib\

REM Copy AdminMessages from konakartadmin to konakart

COPY %1\webapps\konakartadmin\WEB-INF\classes\AdminMessages*.properties %1\webapps\konakart\WEB-INF\classes\

@ECHO OFF
