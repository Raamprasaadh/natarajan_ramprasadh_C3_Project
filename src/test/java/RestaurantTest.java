import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    // This method executes before each test case and create a default restaurant
    public void initialize_restaurant_prior_to_each_test()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    // This method returns the number of items available in the menu of the restaurant
    public int getMenuSize()
    {
        return restaurant.getMenu().size();
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockedRestaurant =  Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(12,30));
        assertTrue(mockedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant mockedRestaurant =  Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(7,30));
        boolean isRestaurantOpen = mockedRestaurant.isRestaurantOpen();

        assertFalse(isRestaurantOpen);

        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(23,30));
        isRestaurantOpen = mockedRestaurant.isRestaurantOpen();

        assertFalse(isRestaurantOpen);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = getMenuSize();

        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,getMenuSize());

        restaurant.addToMenu("Momos",319);
        assertEquals(initialMenuSize+2,getMenuSize());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,getMenuSize());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("Momos"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void when_no_item_is_selected_the_order_total_should_be_0()
    {
        List<String> items = Arrays.asList();
        int totalAmount = restaurant.getOrderTotal(items);
        assertEquals(0,totalAmount);
    }

    @Test
    public void when_a_single_item_is_selected_the_order_total_should_be_the_price_of_the_item()
    {
        List<String> items = Arrays.asList("Sweet corn soup");
        int totalAmount = restaurant.getOrderTotal(items);

        assertEquals(119,totalAmount);
    }

    @Test
    public void when_multiple_items_are_selected_the_total_amount_should_be_total_of_all_items()
    {
        List<String> items = Arrays.asList("Sweet corn soup","Vegetable lasagne");

        int totalAmount = restaurant.getOrderTotal(items);
        assertEquals(388,totalAmount);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}