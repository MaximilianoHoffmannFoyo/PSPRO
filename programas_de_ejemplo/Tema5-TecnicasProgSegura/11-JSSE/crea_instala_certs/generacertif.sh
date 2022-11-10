mkdir certificados
mkdir certificados/servidor
mkdir certificados/cliente

# Generar y exportar certificados de servidor y cliente
keytool -genkey -alias certServ -keyalg RSA -keystore keystoreServ -storepass pwdkeystoreserv
keytool -exportcert -alias certServ -keystore keystoreServ -storepass pwdkeystoreserv -file certificados/servidor/servidor.cer
keytool -genkey -alias certCli -keyalg RSA -keystore keystoreCli -storepass pwdkeystorecli
keytool -exportcert -alias certCli -keystore keystoreCli -storepass pwdkeystorecli -file certificados/cliente/cliente.cer

# Importar como certificados de confianza certificado de servidor para cliente y de cliente para servidor
keytool -importcert -trustcacerts -alias certConfCli -file certificados/servidor/servidor.cer -keystore keystoreCliCertConf -storepass pwdkeystoreconfcli
keytool -importcert -trustcacerts -alias certConfServ -file certificados/cliente/cliente.cer -keystore keystoreServCertConf -storepass pwdkeystoreconfserv
