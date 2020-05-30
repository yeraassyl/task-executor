package kz.kaspi.lab.rest;

import kz.kaspi.lab.db.utils.DBLogger;
import kz.kaspi.lab.process.ProcessManager;
import kz.kaspi.lab.rest.exception.InternalServerError;
import kz.kaspi.lab.rest.exception.ProcessExecutionException;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Path("/")
public class RestWS {

    private static final Logger log = Logger.getLogger(RestWS.class);

    @POST
    @Path("/exec-proc")
    @Produces(MediaType.TEXT_PLAIN)
    public String execProc(@HeaderParam("proc-code") String procCode, String json){
        try {
            LocalDateTime receiveTime = LocalDateTime.now();
            String response = ProcessManager.execute(procCode, json);
            LocalDateTime responseTime = LocalDateTime.now();
            DBLogger.extLog(receiveTime, json, responseTime, response);
            return response;
        } catch (Exception e){
            try {
                DBLogger.errLog(LocalDateTime.now(), e.getMessage());
            } catch (SQLException exception) {
                throw new InternalServerError("Невозможно записать в базу");
            }
            throw new ProcessExecutionException(e.getMessage());
        }
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ". HELLO FROM KASPI-LAB-REST WEB-SERVICE!!!";
    }
}
