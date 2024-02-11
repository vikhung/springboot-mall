package tw.com.vik.constant;

public class MyTest
{
    public static void main(String[] argv)
    {
        ProductCategory category = ProductCategory.FOOD;
        String s = category.name();
        System.out.println(s);
        
        String s2 = "CAR";
        ProductCategory category2 = ProductCategory.valueOf(s2);
        System.out.println(s2);
        
    }

}
