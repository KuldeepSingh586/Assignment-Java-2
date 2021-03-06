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

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kuldeep Singh <c0648442@lambtoncollege.ca>
 */
public class OrderQueueTest {

    public OrderQueueTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));
        orderQueue.add(order);

        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }

    @Test
    public void testWhenCustomerIdDoesNotExistsAndCustomerNameDoesNotExistsThenThrowException() {
        boolean choice = false;
        OrderQueue orderQueue = new OrderQueue();

        Order order = new Order("", "");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));
        try {
            orderQueue.add(order);
        } catch (Exception ex) {
            choice = true;
        }
        assertTrue(choice);

    }

    @Test
    public void testthereIsNoListOfPurchaseThrowException() throws Exception {
        boolean choice = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");

        try {
            orderQueue.add(order);
        } catch (Exception ex) {
            choice = true;
        }
        assertTrue(choice);

    }

    @Test
    public void testWhenRequestNextOrderThenReturnOrderWithEarliestTimeRecivedAndDoesNotHaveTimeProcessed() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));
        orderQueue.add(order);
        Order b = orderQueue.nextOrder();

        assertEquals(order, b);
    }

    @Test
    public void testWhenRequestForNextOrderAndThereIsNoOrderInSystemReturnNull() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order b = orderQueue.nextOrder();
        assertNull(b);
    }

    @Test
    public void testWhenProcessOrderTheOrderHaveTimeRecivedTimeSetProcessedToNow() throws Exception {

        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));
        orderQueue.add(order);

        orderQueue.processOrder(order);

        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }

    @Test
    public void testWhenRequestToProcessOrderAndOrderNotHaveTimeReceivedThenThrowException() throws Exception {
        boolean choice = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));
        try {
            orderQueue.processOrder(order);
            orderQueue.add(order);
        } catch (Exception ex) {

            choice = true;
        }
        assertTrue(choice);

    }

    @Test
    public void testWhenRequestToFilfullOrderAndOrderHasTimeProcessedAndTimeReceivedThenSetTheFulfilledTime() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));
        orderQueue.add(order);
        orderQueue.processOrder(order);
        orderQueue.fulfill(order);

        long expResult = new Date().getTime();
        long result = order.getTimeFulfilled().getTime();
        assertEquals(expResult, result);
    }

    @Test
    public void testWhenRequestToFulFillOrderAndOrderNotHaveTimeProcessedThenThrowException() throws Exception {
        Boolean choice = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));
        orderQueue.add(order);

        try {

            orderQueue.fulfill(order);
        } catch (Exception ex) {
            choice = true;
        }
        assertTrue(choice);
    }

    @Test
    public void testWhenRequestToFulFillOrderAndOrderNotHaveTimeReceivedThenThrowException() throws Exception {
        Boolean choice = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));

        try {

            orderQueue.fulfill(order);
        } catch (Exception ex) {
            choice = true;
        }
        assertTrue(choice);
    }

    @Test
    public void testWhenRequestToReportAndNoOrderInSystemThenRetuenEmptyString() throws Exception {
    OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(0004, 450));
        order.addPurchase(new Purchase(0006, 250));
        orderQueue.add(order);
        
         Order orderNext = new Order("CUST00002", "XYZ Construction");
        orderNext.addPurchase(new Purchase(0007, 451));
        orderNext.addPurchase(new Purchase(0001, 251));
        orderQueue.add(orderNext);

        String report=orderQueue.report(order,"orders");
        long time=new Date().getTime();
        
    }
}
