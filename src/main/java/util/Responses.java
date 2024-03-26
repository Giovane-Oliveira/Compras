package util;

import java.util.UUID;

public class Responses {



	
	public String gerarToken(String perfil) {
		
		  UUID uuid = UUID.randomUUID();
		  String myRandom = uuid.toString();
		  String token = null;
		  
		  if(perfil.equalsIgnoreCase("supervisor")) {
			  
			  token = myRandom.substring(0,5);
				 
			  }else if(perfil.equalsIgnoreCase("gerente")) {
				  
				  token = myRandom.substring(0,6);
				  
			  }else if(perfil.equalsIgnoreCase("colaborador")) {
				  
				  token = myRandom.substring(0,7);
				  
			  }else if(perfil.equalsIgnoreCase("aleatorio")) {
				  
				  token = myRandom.substring(0,8);
				  
			  }
		  
		  
		
		  
		  
		  return token;
		
		
		
	}
	
	
}
