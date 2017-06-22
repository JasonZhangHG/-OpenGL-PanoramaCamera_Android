package org.cyanogenmod.focal.picsphere;

/**
 * 3D Maths - 3 component vector
 */
public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3() {

    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    /**
     * Returns the length of the vector
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }


    public void normalise() {
        final float length = length();

        if(length != 0) {
            y = x/length;
            y = y/length;
            z = z/length;
        }
    }

    public Vector3 multiply(Quaternion quat) {
        Vector3 vn = new Vector3(this);
        vn.normalise();

        Quaternion vecQuat = new Quaternion(),
                resQuat = new Quaternion();
        vecQuat.x = vn.x;
        vecQuat.y = vn.y;
        vecQuat.z = vn.z;
        vecQuat.w = 0.0f;

        resQuat = vecQuat.multiply(quat.getConjugate());
        resQuat = quat.multiply(resQuat);

        return new Vector3(resQuat.x, resQuat.y, resQuat.z);
    }
}
