package framework;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Vector;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;

public class Utilitaire {
    public String to_getAttribu(String nomAttribu,String typeField)
    {
          String getAttribMaj="get";
          if(typeField.compareToIgnoreCase("boolean")==0){  getAttribMaj="is";  }
          String attrib=nomAttribu.substring(0,1).toUpperCase(); //rendre en majuscul la premiere lettre du nomAtribu
          String restattrib=nomAttribu.substring(1,nomAttribu.length());  //prendre les lettres a partir du 2e lettre
          getAttribMaj=getAttribMaj.concat(attrib+restattrib);  //fusionner pour avoir le nom de fonction "getAtribu"
                       //Method m=c.getMethod("getVar1");
          return getAttribMaj;
    }
    public String to_setAttribu(String nomAttribu)
    {
          String getAttribMaj="set";
          String attrib=nomAttribu.substring(0,1).toUpperCase(); //rendre en majuscul la premiere lettre du nomAtribu
          String restattrib=nomAttribu.substring(1,nomAttribu.length());  //prendre les lettres a partir du 2e lettre=
          getAttribMaj=getAttribMaj.concat(attrib+restattrib); //fusionner pour avoir le nom de fonction "getAtribu"
           //Method m=c.getMethod("getVar1");
          return getAttribMaj;
    }

      public Object valuOfString(String type, String value){
            Object object=value;
            if(type.compareTo("int")==0){
                  object=Integer.valueOf(value);
            }else if(type.compareTo("long")==0){
                  object=Long.valueOf(value);
            }else if(type.compareTo("float")==0){
                  object=Float.valueOf((value));
            }else if(type.compareTo("char")==0){
                  String str=value;
                  object=str.charAt(0);
            }else if(type.compareTo("double")==0){
                  object=Double.valueOf(value);
            }else if(type.compareTo("LocalDate")==0){ //annee-mois-jours
                  String [] strDt=value.split("-");
                  String[] forJour=strDt[2].split(" ");
                  LocalDate date=LocalDate.of(Integer.valueOf(strDt[0]), Integer.valueOf(strDt[1]) , Integer.valueOf(forJour[0]) );
                  object=date;
            }else if(type.compareTo("Date")==0){
                  Date date=Date.valueOf(value);
                  object=date;
            }else if(type.compareTo("Time")==0){
                  Time time=Time.valueOf(value);
                  object=time;
            }else if(type.compareTo("boolean")==0){
                  object=Boolean.valueOf(value);
            }
            return object;
      }

    public Object createIfExisteRequest(Class classe,HttpServletRequest request)throws Exception, ServletException, IOException {
      Object object=classe.newInstance();
      Field[] fields= classe.getDeclaredFields();
      String rqsVal="";
      Object param=null;
      boolean exist=false;
      if(request!=null){
            for(int i=0;i<fields.length;i++){
                  rqsVal=request.getParameter(fields[i].getName());
                  if(rqsVal!=null && rqsVal!=""){
                        //param=fields[i].getType().cast(rqsVal);
                        param=valuOfString(fields[i].getType().getSimpleName(), rqsVal);
                        object.getClass().getDeclaredMethod( to_setAttribu(fields[i].getName()) ,fields[i].getType() ).invoke(object,param);
                        exist=true;
                  }
            }
      }
      if(exist==false){ return null; }
      return object;
    }

    public Object[] createAllIfExisteRequest(Class[] classes,HttpServletRequest request)throws Exception, ServletException, IOException {
      Vector vObjs=new Vector();
      Object obj=null;
      for(int i=0;i<classes.length;i++){ 
            obj=createIfExisteRequest(classes[i],request);
            if(obj!=null){ vObjs.add(obj); } 
      }
      if(vObjs.size()<1){ return null;}
      Object[] objects=new Object[vObjs.size()];
      for(int i=0;i<vObjs.size();i++){   objects[i]=vObjs.elementAt(i); }
      return objects;
    }
   

}
