/*
 * (C) Copyright 2015-2016 Richard Greenlees
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package org.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 4 ints and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public class Vector4i implements Externalizable, Vector4ic {

    private final class Proxy implements Vector4ic {
        private final Vector4ic delegate;

        Proxy(Vector4ic delegate) {
            this.delegate = delegate;
        }

        public int x() {
            return delegate.x();
        }

        public int y() {
            return delegate.y();
        }

        public int z() {
            return delegate.z();
        }

        public int w() {
            return delegate.w();
        }

        public IntBuffer get(IntBuffer buffer) {
            return delegate.get(buffer);
        }

        public IntBuffer get(int index, IntBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public ByteBuffer get(ByteBuffer buffer) {
            return delegate.get(buffer);
        }

        public ByteBuffer get(int index, ByteBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public Vector4i sub(Vector4ic v, Vector4i dest) {
            return delegate.sub(v, dest);
        }

        public Vector4i sub(int x, int y, int z, int w, Vector4i dest) {
            return delegate.sub(x, y, z, w, dest);
        }

        public Vector4i add(Vector4ic v, Vector4i dest) {
            return delegate.add(v, dest);
        }

        public Vector4i add(int x, int y, int z, int w, Vector4i dest) {
            return delegate.add(x, y, z, w, dest);
        }

        public Vector4i mul(Vector4ic v, Vector4i dest) {
            return delegate.mul(v, dest);
        }

        public Vector4i div(Vector4ic v, Vector4i dest) {
            return delegate.div(v, dest);
        }

        public Vector4i mul(float scalar, Vector4i dest) {
            return delegate.mul(scalar, dest);
        }

        public Vector4i div(float scalar, Vector4i dest) {
            return delegate.div(scalar, dest);
        }

        public long lengthSquared() {
            return delegate.lengthSquared();
        }

        public double length() {
            return delegate.length();
        }

        public double distance(Vector4ic v) {
            return delegate.distance(v);
        }

        public double distance(int x, int y, int z, int w) {
            return delegate.distance(x, y, z, w);
        }

        public int distanceSquared(Vector4ic v) {
            return delegate.distanceSquared(v);
        }

        public int distanceSquared(int x, int y, int z, int w) {
            return delegate.distanceSquared(x, y, z, w);
        }

        public int dot(Vector4ic v) {
            return delegate.dot(v);
        }

        public Vector4i negate(Vector4i dest) {
            return delegate.negate(dest);
        }
    }

    private static final long serialVersionUID = 1L;

    /**
     * The x component of the vector.
     */
    public int x;
    /**
     * The y component of the vector.
     */
    public int y;
    /**
     * The z component of the vector.
     */
    public int z;
    /**
     * The w component of the vector.
     */
    public int w;

    /**
     * Create a new {@link Vector4i} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4i() {
        this.w = 1;
    }

    /**
     * Create a new {@link Vector4i} with the same values as <code>v</code>.
     *
     * @param v
     *          the {@link Vector4ic} to copy the values from
     */
    public Vector4i(Vector4ic v) {
        if (v instanceof Vector4i) {
            MemUtil.INSTANCE.copy((Vector4i) v, this);            
        } else {
            this.x = v.x();
            this.y = v.y();
            this.z = v.z();
            this.w = v.w();
        }
    }

    /**
     * Create a new {@link Vector4i} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     *
     * @param v
     *          the {@link Vector3ic}
     * @param w
     *          the w component
     */
    public Vector4i(Vector3ic v, int w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4i} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4i(Vector2ic v, int z, int w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4i} and initialize all four components with the
     * given value.
     *
     * @param s
     *          scalar value of all four components
     */
    public Vector4i(int s) {
        MemUtil.INSTANCE.broadcast(s, this);
    }

    /**
     * Create a new {@link Vector4i} with the given component values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector4i(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     *
     * @see #Vector4i(int, ByteBuffer)
     * 
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link IntBuffer} at the current buffer
     * {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #Vector4i(int, IntBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #Vector4i(int, IntBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(IntBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link IntBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#x()
     */
    public int x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#y()
     */
    public int y() {
        return this.y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#z()
     */
    public int z() {
        return this.z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#w()
     */
    public int w() {
        return this.w;
    }

    /**
     * Set this {@link Vector4i} to the values of the given <code>v</code>.
     *
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4i set(Vector4ic v) {
        if (v instanceof Vector4i) {
            MemUtil.INSTANCE.copy((Vector4i) v, this);            
        } else {
            this.x = v.x();
            this.y = v.y();
            this.z = v.z();
            this.w = v.w();
        }
        return this;
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     *
     * @param v
     *          the {@link Vector3ic} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4i set(Vector3ic v, int w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    /**
     * Sets the first two components of this to the components of given
     * <code>v</code> and last two components to the given <code>z</code>, and
     * <code>w</code>.
     *
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4i set(Vector2ic v, int z, int w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the x, y, z, and w components to the supplied value.
     *
     * @param s
     *          the value of all four components
     * @return this
     */
    public Vector4i set(int s) {
        MemUtil.INSTANCE.broadcast(s, this);
        return this;
    }

    /**
     * Set the x, y, z, and w components to the supplied values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4i set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #set(int, ByteBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #set(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4i set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4i set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    /**
     * Read this vector from the supplied {@link IntBuffer} at the current
     * buffer {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #set(int, IntBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #set(int, IntBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4i set(IntBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link IntBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4i set(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <tt>[0..3]</tt>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..3]</tt>
     */
    public Vector4i setComponent(int component, int value) throws IllegalArgumentException {
        switch (component) {
            case 0:
                x = value;
                break;
            case 1:
                y = value;
                break;
            case 2:
                z = value;
                break;
            case 3:
                w = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#get(java.nio.IntBuffer)
     */
    public IntBuffer get(IntBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#get(int, java.nio.IntBuffer)
     */
    public IntBuffer get(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4i sub(Vector4ic v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        w -= v.w();
        return this;
    }

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param w
     *          the w component to subtract
     * @return this
     */
    public Vector4i sub(int x, int y, int z, int w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#sub(org.joml.Vector4ic, org.joml.Vector4i)
     */
    public Vector4i sub(Vector4ic v, Vector4i dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        dest.z = z - v.z();
        dest.w = w - v.w();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#sub(int, int, int, int, org.joml.Vector4i)
     */
    public Vector4i sub(int x, int y, int z, int w, Vector4i dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        dest.w = this.w - w;
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     *
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector4i add(Vector4ic v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#add(org.joml.Vector4ic, org.joml.Vector4i)
     */
    public Vector4i add(Vector4ic v, Vector4i dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        dest.z = z + v.z();
        dest.w = w + v.w();
        return dest;
    }

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param z
     *          the z component to add
     * @param w
     *          the w component to add
     * @return this
     */
    public Vector4i add(int x, int y, int z, int w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#add(int, int, int, int, org.joml.Vector4i)
     */
    public Vector4i add(int x, int y, int z, int w, Vector4i dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        dest.w = this.w + w;
        return dest;
    }

    /**
     * Multiply this Vector4i component-wise by another Vector4i.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4i mul(Vector4ic v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        w *= v.w();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#mul(org.joml.Vector4ic, org.joml.Vector4i)
     */
    public Vector4i mul(Vector4ic v, Vector4i dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        dest.w = w * v.w();
        return dest;
    }

    /**
     * Divide this Vector4i component-wise by another Vector4i.
     *
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector4i div(Vector4ic v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        w /= v.w();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#div(org.joml.Vector4ic, org.joml.Vector4i)
     */
    public Vector4i div(Vector4ic v, Vector4i dest) {
        dest.x = x / v.x();
        dest.y = y / v.y();
        dest.z = z / v.z();
        dest.w = w / v.w();
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector4i} by the given scalar
     * value.
     *
     * @param scalar
     *          the scalar to multiply by
     * @return this
     */
    public Vector4i mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#mul(float, org.joml.Vector4i)
     */
    public Vector4i mul(float scalar, Vector4i dest) {
        dest.x = (int) (x * scalar);
        dest.y = (int) (y * scalar);
        dest.z = (int) (z * scalar);
        dest.w = (int) (w * scalar);
        return dest;
    }

    /**
     * Divide all components of this {@link Vector4i} by the given scalar value.
     *
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector4i div(int scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#div(float, org.joml.Vector4i)
     */
    public Vector4i div(float scalar, Vector4i dest) {
        dest.x = (int) (x / scalar);
        dest.y = (int) (y / scalar);
        dest.z = (int) (z / scalar);
        dest.w = (int) (w / scalar);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#lengthSquared()
     */
    public long lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#distance(org.joml.Vector4i)
     */
    public double distance(Vector4ic v) {
        return Math.sqrt(distanceSquared(v));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#distance(int, int, int, int)
     */
    public double distance(int x, int y, int z, int w) {
        return Math.sqrt(distanceSquared(x, y, z, w));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#distanceSquared(org.joml.Vector4ic)
     */
    public int distanceSquared(Vector4ic v) {
        int dx = this.x - v.x();
        int dy = this.y - v.y();
        int dz = this.z - v.z();
        int dw = this.w - v.w();
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#distanceSquared(int, int, int, int)
     */
    public int distanceSquared(int x, int y, int z, int w) {
        int dx = this.x - x;
        int dy = this.y - y;
        int dz = this.z - z;
        int dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#dot(org.joml.Vector4ic)
     */
    public int dot(Vector4ic v) {
        return x * v.x() + y * v.y() + z * v.z() + w * v.w();
    }

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector4i zero() {
        MemUtil.INSTANCE.zero(this);
        return this;
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector4i negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4ic#negate(org.joml.Vector4i)
     */
    public Vector4i negate(Vector4i dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = -w;
        return dest;
    }

    /**
     * Return a string representation of this vector.
     *
     * @return the string representation
     */
    public String toString() {
        return "(" + x + " " + y + " " + z + " " + w + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    /**
     * Return a string representation of this vector by formatting the vector
     * components with the given {@link NumberFormat}.
     *
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        out.writeInt(w);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        w = in.readInt();
    }

    /**
     * Set the components of this vector to be the component-wise minimum of
     * this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4i min(Vector4ic v) {
        this.x = Math.min(x, v.x());
        this.y = Math.min(y, v.y());
        this.z = Math.min(z, v.z());
        this.w = Math.min(w, v.w());
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of
     * this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4i max(Vector4ic v) {
        this.x = Math.max(x, v.x());
        this.y = Math.max(y, v.y());
        this.z = Math.max(z, v.z());
        this.w = Math.min(w, v.w());
        return this;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        result = prime * result + w;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector4i other = (Vector4i) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        if (w != other.w) {
            return false;
        }
        return true;
    }

    /**
     * Create a new immutable view of this {@link Vector4i}.
     * <p>
     * The observable state of the returned object is the same as that of <code>this</code>, but casting
     * the returned object to Vector4i will not be possible.
     * <p>
     * This method allocates a new instance of a class implementing Vector4ic on every call.
     * 
     * @return the immutable instance
     */
    public Vector4ic toImmutable() {
        if (!Options.DEBUG)
            return this;
        return new Proxy(this);
    }

}
