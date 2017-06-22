package org.cyanogenmod.focal;

/**
 * XMP Helper to write XMP data to files
 */
public class XMPHelper {
    static {
        System.loadLibrary("xmptoolkit");
        System.loadLibrary("xmphelper_jni");
    }

    /**
     * Writes the provided XMP Data to the specified fileName. This goes through
     * Adobe's XMP toolkit library.
     *
     * @param fileName The file name to which write the XMP metadata
     * @param xmpData The RDF-formatted data
     * @return -1 if error, 0 if everything went fine
     */
    public native int writeXmpToFile(String fileName, String xmpData);
}
