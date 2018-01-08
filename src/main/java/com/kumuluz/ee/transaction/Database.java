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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Database {
    private static List<Transaction> transactions = new ArrayList<>(
            Arrays.asList(
                new Transaction("0", "0", "0", new Date()) //dummy
            )
    );



    public static List<Transaction> getTransactions() {
        return transactions;
    }

    public static Transaction getTransaction(String id) {

        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id))
                return transaction;
        }

        return null;
    }


    public static void addTransaction(Transaction transaction) {

        String lastId = transactions.get(transactions.size() - 1).getId();
        String id = Integer.toString(Integer.valueOf(lastId) + 1);
        transaction.setId(id);
        transaction.setExecutionDate(new Date());
        transactions.add(transaction);
    }

    public static void deleteTransaction(String id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id)) {
                transactions.remove(transaction);
                break;
            }
        }
    }
}
