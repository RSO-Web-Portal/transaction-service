/*
 *  Copyright (c) 2014-2017 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
*/
package com.kumuluz.ee.transaction;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;


@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("transactions")
@Log(LogParams.METRICS)
public class TransactionResource {


    @Inject
    @DiscoverService(value = "catalogue-service", version = "1.0.x", environment = "dev")
    private Optional<WebTarget> catalogueTarget;

    @Inject
    @DiscoverService(value = "account-service", version = "1.0.x", environment = "dev")
    private Optional<WebTarget> accountTarget;




    @GET
    public Response getAllTransactions() {
        List<Transaction> transactions = Database.getTransactions();
        return Response.ok(transactions).build();
    }


    @GET
    @Path("{id}")
    public Response getTransaction(@PathParam("id") String id) {

        Transaction transaction= Database.getTransaction(id);
        return transaction != null
                ? Response.ok(transaction).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}/product")
    public Response getTransactionProduct(@PathParam("id") String id) {

        Transaction transaction = Database.getTransaction(id);

        if (transaction == null) {
            Response.status(Response.Status.NOT_FOUND).build();
        }


        if (!catalogueTarget.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        WebTarget catalogueService;



        Response response;
        try {


            catalogueService = catalogueTarget.get().path("v1/products/" + transaction.getProductId());
            response = catalogueService.request().get();
            Product p = response.readEntity(Product.class);

            return Response.status(200).entity(p).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(408).build();
        }

    }

    @GET
    @Path("{id}/account")
    public Response getTransactionAccount(@PathParam("id") String id) {

        Transaction transaction = Database.getTransaction(id);

        if (transaction == null) {
            Response.status(Response.Status.NOT_FOUND).build();
        }


        if (!accountTarget.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        WebTarget accountService;



        Response response;
        try {


            accountService = accountTarget.get().path("v1/accounts/" + transaction.getAccountId());
            response = accountService.request().get();
            Account a = response.readEntity(Account.class);

            return Response.status(200).entity(a).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(408).build();
        }

    }


    @POST
    public Response addTransaction(Transaction t) {

        if (!catalogueTarget.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        WebTarget catalogueService;

        Response response;
        try {


            catalogueService = catalogueTarget.get().path("v1/products/" + t.getProductId() + "/makeTransaction");
            //response = catalogueService.request(MediaType.APPLICATION_JSON).put(null);
            response = catalogueService.request().get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                Database.addTransaction(t);
                return Response.ok().build();
            }

            return Response.status(Response.Status.NOT_MODIFIED).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(408).build();
        }

    }


    @DELETE
    @Path("{id}")
    public Response deleteTransaction(@PathParam("id") String id) {
        Database.deleteTransaction(id);
        return Response.ok().build();
    }
}
