package util;

import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntranetUtils {

    static Logger logger = LoggerFactory.getLogger( IntranetUtils.class );
    
    private static final char[] banglaDigits = {
            '০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯'
    };
    private static final char[] englishDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    private final int nCharacter = 150;

    public static String getCurrentAddress( HttpServletRequest request ) {

        String queryString = request.getQueryString();
        String encodedUrl = "";

        if ( null == queryString ) {
            queryString = "";
        }

        try {

            encodedUrl = URLEncoder.encode( request.getRequestURL().append( "?" + queryString ).toString(), "UTF-8" );

        } catch ( Exception e ) {

            e.printStackTrace();
            encodedUrl = "";

        }
        return encodedUrl;
        // return
        // request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getPathInfo()
        // + (request.getQueryString() != null ? "?" +request.getQueryString() :
        // "");
    }

    public static String getCurrentAddress( HttpServletRequest request, String actionPath ) {

        String queryString = request.getQueryString();
        String encodedUrl = "";

        if ( null == queryString ) {
            queryString = "";
        }

        try {

            encodedUrl = URLEncoder.encode( request.getContextPath() + "/" + actionPath + "?" + queryString, "UTF-8" );

        } catch ( Exception e ) {

            e.printStackTrace();
            encodedUrl = "";

        }
        return encodedUrl;
        // return
        // request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getPathInfo()
        // + (request.getQueryString() != null ? "?" +request.getQueryString() :
        // "");
    }

    public static final String getDigitBanglaFromEnglish( String number ) {

        if ( number == null )
            return new String( "" );
        StringBuilder builder = new StringBuilder();
        try {
            for ( int i = 0; i < number.length(); i++ ) {
                if ( Character.isDigit( number.charAt( i ) ) ) {
                    if ( ( ( int ) ( number.charAt( i ) ) - 48 ) <= 9 ) {
                        builder.append( banglaDigits[( int ) ( number.charAt( i ) ) - 48] );
                    } else {
                        builder.append( number.charAt( i ) );
                    }
                } else {
                    builder.append( number.charAt( i ) );
                }
            }
        } catch ( Exception e ) {
            logger.debug( "getDigitBanglaFromEnglish: ", e );
            return new String( "" );
        }
        return builder.toString();
    }

    public static final String getDigitBanglaFromEnglish( Object obj ) {

        StringBuilder builder = new StringBuilder();
        try {
            String number = obj.toString();
            for ( int i = 0; i < number.length(); i++ ) {
                if ( Character.isDigit( number.charAt( i ) ) ) {
                    if ( ( ( int ) ( number.charAt( i ) ) - 48 ) <= 9 ) {
                        builder.append( banglaDigits[( int ) ( number.charAt( i ) ) - 48] );
                    } else {
                        builder.append( number.charAt( i ) );
                    }
                } else {
                    builder.append( number.charAt( i ) );
                }
            }
        } catch ( Exception e ) {
            logger.debug( "getDigitBanglaFromEnglish: ", e );
            return new String( "" );
        }
        return builder.toString();
    }

    public static final String getDigitEnglishFromBangla( String number ) {

        StringBuilder builder = new StringBuilder();
        for ( int i = 0; i < number.length(); i++ ) {
            if ( number.charAt( i ) == '০' )
                builder.append( englishDigits[0] );
            else if ( number.charAt( i ) == '১' )
                builder.append( englishDigits[1] );
            else if ( number.charAt( i ) == '২' )
                builder.append( englishDigits[2] );
            else if ( number.charAt( i ) == '৩' )
                builder.append( englishDigits[3] );
            else if ( number.charAt( i ) == '৪' )
                builder.append( englishDigits[4] );
            else if ( number.charAt( i ) == '৫' )
                builder.append( englishDigits[5] );
            else if ( number.charAt( i ) == '৬' )
                builder.append( englishDigits[6] );
            else if ( number.charAt( i ) == '৭' )
                builder.append( englishDigits[7] );
            else if ( number.charAt( i ) == '৮' )
                builder.append( englishDigits[8] );
            else if ( number.charAt( i ) == '৯' )
                builder.append( englishDigits[9] );
            else
                builder.append( number.charAt( i ) );

        }
        return builder.toString();
    }

    public static final String getMonthNameinBangla( int month ) {

        String[] monthNameBangla = {
                "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর",
                "নভেম্বর", "ডিসেম্বর"
        };
        return monthNameBangla[month - 1];
    }

    public static final String getMonthNameinBanglaZeroBasedIndexing( int month ) {

        String[] monthNameBangla = {
                "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর",
                "নভেম্বর", "ডিসেম্বর"
        };
        return monthNameBangla[month];
    }

    public static final String getNcharacters( int n, String s ) {

        if ( s == null || s.equals( "" ) )
            return "";
        return s.substring( 0, Math.min( s.length(), n ) );
    }

    public static final long[] times = {
            TimeUnit.DAYS.toMillis( 365 ),
            TimeUnit.DAYS.toMillis( 30 ),
            TimeUnit.DAYS.toMillis( 1 ),
            TimeUnit.HOURS.toMillis( 1 ),
            TimeUnit.MINUTES.toMillis( 1 ),
            TimeUnit.SECONDS.toMillis( 1 )
    };
    public static final String[] timesString = {
            "বছর", "মাস", "দিন", "ঘণ্টা", "মিনিট", "সেকেন্ড"
    };

    public static final String getDateDiffercenceFromNow( Long pastDate ) {

        Date d2 = new Date();
        Date d1 = new Date( pastDate );
        long diff = d2.getTime() - d1.getTime();
        StringBuffer res = new StringBuffer();
        for ( int i = 0; i < times.length; i++ ) {
            Long current = times[i];
            long temp = diff / current;
            if ( temp > 0 ) {
                res.append( temp ).append( " " ).append( timesString[i] ).append( temp > 1 ? "" : "" ).append( " আগে" );
                break;
            }
        }
        if ( "".equals( res.toString() ) )
            return "০ সেকেন্ড আগে";
        else
            return getDigitBanglaFromEnglish( res.toString() );
    }

    public static boolean isNumeric( String str ) {

        try {
           
            Double.parseDouble( str );
            
        } catch ( NumberFormatException nfe ) {
            return false;
        }
        return true;
    }

    public static String encryptDecryptProcess( String plain ) {

        if ( plain == null || plain.isEmpty() || plain == "" ) {
            return new String( "" );
        }
        byte[] buffer = plain.trim().getBytes();
        try {
            for ( int i = 0; i < buffer.length; i++ ) {
                buffer[i] = ( byte ) ( buffer[i]
                        ^ IntranetConstants.encryptKey[i % IntranetConstants.encryptKey.length] );
            }
        } catch ( Exception e ) {
            logger.debug( "Error in encription-decryption", e );
            return new String( "" );
        }
        return new String( buffer );
    }
}
