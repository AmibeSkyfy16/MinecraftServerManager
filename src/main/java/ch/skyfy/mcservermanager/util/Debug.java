package ch.skyfy.mcservermanager.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class Debug {
    public static void DebugURL(URL url) {
        System.out.println("\n-------------- DEBUG URL --------------");
        System.out.println("externalForm : " + url.toExternalForm());
        System.out.println("toString : " + url.toString());
        System.out.println("getAutority : " + url.getAuthority());
        System.out.println("getFile : " + url.getFile());
        System.out.println("getPath : " + url.getPath());
        System.out.println("getHost : " + url.getHost());
        System.out.println("getQuery : " + url.getQuery());
        System.out.println("getProtocol : " + url.getProtocol());
    }

    public static void DebugURI(URI uri) {
        System.out.println("\n-------------- DEBUG URL --------------");
        System.out.println("getRawPath : " + uri.getRawPath());
        System.out.println("toString : " + uri.toString());
        System.out.println("getAutority : " + uri.getAuthority());
        System.out.println("getFragment : " + uri.getFragment());
        System.out.println("getPath : " + uri.getPath());
        System.out.println("getHost : " + uri.getHost());
        System.out.println("getQuery : " + uri.getQuery());
        System.out.println("getUserInfo : " + uri.getUserInfo());
    }

    public static void DebugFile(File file) throws IOException {
        System.out.println("\n-------------- DEBUG URL --------------");
        System.out.println("exists : " + file.exists());
        System.out.println("toString : " + file.toString());
        System.out.println("isDirectory : " + file.isDirectory());
        System.out.println("getAbsolutePath : " + file.getAbsolutePath());
        System.out.println("getPath : " + file.getPath());
        System.out.println("getCanonicalPath : " + file.getCanonicalPath());
        System.out.println("isFile : " + file.isFile());
    }
}
