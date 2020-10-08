package biz.majorov.expenses
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.quarkus.kotlin.routes
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces


/**
 * camel routes definition
 */

@ApplicationScoped
class Routes {
    @Produces
    fun myRoutes() = routes {
        from("direct:select-all-expenses").to("sql:select * from EXPENSES ORDER BY ID")
                .log("\${body}")
        from("direct:insert-expense")
                .log("insert-expenses route body: \$simple{body.description},\$simple{body.amount}, \$simple{body.createdAT}")
        .to("sql:INSERT INTO EXPENSES (DESCRIPTION, AMOUNT ,CREATED,FK_REPORT) " +
                "VALUES (:#\$simple{body.description},:#\$simple{body.amount}," +
                "to_date(:#\$simple{body.createdAT.toString},'YYYY-MM-DD'), "
                +":#\$simple{body.report})")
        from("direct:select-one-expense").to("sql:select * from EXPENSES WHERE ID=:#\${body}").log("\${body}")
        from("direct:update-expense").to("sql:UPDATE EXPENSES SET AMOUNT = :#\$simple{body.amount} , DESCRIPTION=:#\$simple{body.description}," +
                "  CREATED = to_date(:#\$simple{body.createdAT.toString},'YYYY-MM-DD'),  TSTAMP = now(), FK_REPORT=:#\$simple{body.report} " +
                " WHERE ID= :#\$simple{body.id}")
        from("direct:delete-expense").to("sql:DELETE FROM EXPENSES  WHERE EXPENSES.ID =:#\${body}")
        //user operations
        // the user coming from sso so we have to save it before run other operations
        from("direct:select-user-by-name").to("sql:SELECT  ID, NAME  FROM  app_user WHERE NAME LIKE :#\${body}").log("rows: \${body}")

        from("direct:insert-user").to("sql:INSERT INTO APP_USER (NAME) VALUES (:#\${body})")
        

	from("direct:select-all-reports").log("get reports for user with id: \${body}")
                .to("sql:select * from report where fk_app_user = :#\${body}")
                .log("\${body}")

        from("direct:delete-report").to("sql:DELETE FROM report  WHERE report.id =:#\${body}")

        //from("direct:insert-report").to("INSERT INTO REPORT (NAME, CREATED,fk_app_user) " +
          //          "VALUES (3, 'Simple Report2','2019-07-30', (select DISTINCT id from app_user where name LIKE 'niko'))"
           // )

    }

}


/**
class MyRoute : RouteBuilder() {
    @Throws(Exception::class)
    override fun configure() {
        from("timer:tick")
                .setBody().constant("hello")
                .to("direct:log")
        from("direct:log")
                .log("\${body}")
    }
}

 ***/
