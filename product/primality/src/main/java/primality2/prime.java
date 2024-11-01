package primality2;

public class prime {
  public boolean isprime(long n) {
    if (n<=1) {
      return false;}
    for (long i=2; i<n; i++) {
      if (n%i==0) {
        return false;
        }
      }
    return true;
    }
    
  }
