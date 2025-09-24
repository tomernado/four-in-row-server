package org.example.fourinline.model;

public class Player
{
    public final String id;
    public final char symbol;

    public Player(String id, char symbol)
    {
      if (id==null || id.isEmpty() )
          {
          throw new IllegalArgumentException("Player id cannot be null or empty");
          }
      if (symbol != 'R' && symbol != 'Y')
      {
          throw new IllegalArgumentException("Player symbols must be R or Y");
      }
      this.id = id;
      this.symbol = symbol;
    }

}
