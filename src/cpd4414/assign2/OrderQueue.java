/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd4414.assign2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Kuldeep Singh < c0648442@lambtoncollege.ca>
 */
public class OrderQueue {

    Queue<Order> orderQueue = new ArrayDeque<>();
    Queue<Order> orderProcessQueue = new ArrayDeque<>();

    public void add(Order order) throws Exception {

        if ((order.getCustomerId().isEmpty()) && (order.getCustomerName().isEmpty())) {
            throw new Exception("Customer ID and customer name not found exception");
        }
        if (order.getListOfPurchases().isEmpty()) {
            throw new Exception("No list of puchase found exception");
        } else {
            orderQueue.add(order);
            order.setTimeReceived(new Date());
        }

    }

    public Order nextOrder() {
        return orderQueue.peek();

    }

    public void remove(Order order) {
        orderQueue.remove(order);
    }

    public void processOrder(Order order) throws Exception {
        boolean value = true;
        if (order.getTimeReceived() == null) {
            throw new Exception("Order does not have time received");
        } else {
            for (Purchase p : order.getListOfPurchases()) {
                if (Inventory.getQuantityForId(p.getProductId()) <= p.getQuantity()) {
                    value = false;
                }
            }

        }
        if (value == false) {
            order.setTimeProcessed(new Date());
        } else {
            order.setTimeProcessed(new Date());
            orderQueue.add(order);
            orderQueue.remove(order);
        }
    }

    public void fulfill(Order order) throws Exception {
        if (order.getTimeProcessed() == null) {
            throw new Exception("Order does not have Time Processed");
        }
        if (order.getTimeReceived() == null) {
            throw new Exception("Order does not have Received");
        } else {
            order.setTimeFulfilled(new Date());
        }
    }

    public String report(Order order, String listTypeOrder) {
        
            String str="";
                LinkedHashMap m1 = new LinkedHashMap();
                LinkedList l1 = new LinkedList();
              
                m1.put("customerId", order.getCustomerId());
                m1.put("customerName", order.getCustomerName());
                m1.put("timeReceived", order.getTimeReceived());
                m1.put("timeProcessed", order.getTimeProcessed());
                m1.put("timeFulfilled", order.getTimeFulfilled());
                return str;

    }
}
