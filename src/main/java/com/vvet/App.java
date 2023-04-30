package com.vvet;

public class App 
{
    public static void main( String[] args )
    {
      try {
        Example.main(args);
        DotParser.parse("src/assets/example.dot");
      } catch (Exception e) {
        System.out.println(e);
      }
    }
}
