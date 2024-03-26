package model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Email {
 
//Método que envia o email
public static void enviaEmail( String remetente, String destinatario, String assunto, String mensagem){
 
Session session = Session.getDefaultInstance(getPropriedades(), getAuthenticator());
 
try {
 
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(remetente)); //Seta o remetente
message.setRecipients(Message.RecipientType.TO,
InternetAddress.parse(destinatario)); //Define o destinatário
message.setSubject(assunto); //Define o assunto
message.setText(mensagem); //Mensagem do email
 
Transport.send(message); //Envia o email
 
} catch (MessagingException e) {
throw new RuntimeException(e);
}
}
 
//Método que retorna a autenticação de sua conta de email
public static Authenticator getAuthenticator(){
 
Authenticator autenticacao = new Authenticator() {
 
public PasswordAuthentication getPasswordAuthentication() {
 
//Preencha com seu email e com sua senha
return new PasswordAuthentication("nao-responda@provida.med.br", "HJ^c+4_gAwiF"); //senha de app / segurança de dois fatores
}
};
 
return autenticacao;
}
 
//Método que retorna as propriedades de configuração do servidor de email
public static Properties getPropriedades(){
 
Properties props = new Properties();
props.put("mail.smtp.host", "mail.provida.med.br"); //SMTP do seu servidor de email
//props.put("mail.smtp.socketFactory.port", "465"); //Porta do servidor smtp
//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //Define a conexão do tipo SSL
props.put("mail.smtp.auth", "true"); //Define que é necessário autenticação
props.put("mail.smtp.port", "587"); //Porta do seu servidor smtp
//props.put("mail.debug", "true");
 
return props;
}
}