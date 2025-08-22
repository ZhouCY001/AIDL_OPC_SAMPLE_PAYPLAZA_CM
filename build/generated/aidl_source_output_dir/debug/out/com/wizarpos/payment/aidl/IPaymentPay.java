/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.wizarpos.payment.aidl;
public interface IPaymentPay extends android.os.IInterface
{
  /** Default implementation for IPaymentPay. */
  public static class Default implements com.wizarpos.payment.aidl.IPaymentPay
  {
    /**
        * execute transaction with json parameters
        * this is a synchronized method and it will be blocked until return the payment result
        * */
    @Override public java.lang.String transact(java.lang.String jsonData) throws android.os.RemoteException
    {
      return null;
    }
    /**
    	* add callback during payment steps, used for prompt User procedure information
    	*/
    @Override public void addProcedureCallback(com.wizarpos.payment.aidl.IPaymentPayCallback callBack) throws android.os.RemoteException
    {
    }
    /**
        * cancel previous request and will make transact return
        * this method return false when payment action can not be cancelled
        */
    @Override public boolean cancelRequest(java.lang.String jsonData) throws android.os.RemoteException
    {
      return false;
    }
    @Override public java.lang.String setParam(java.lang.String jsonData) throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.wizarpos.payment.aidl.IPaymentPay
  {
    private static final java.lang.String DESCRIPTOR = "com.wizarpos.payment.aidl.IPaymentPay";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.wizarpos.payment.aidl.IPaymentPay interface,
     * generating a proxy if needed.
     */
    public static com.wizarpos.payment.aidl.IPaymentPay asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.wizarpos.payment.aidl.IPaymentPay))) {
        return ((com.wizarpos.payment.aidl.IPaymentPay)iin);
      }
      return new com.wizarpos.payment.aidl.IPaymentPay.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_transact:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _result = this.transact(_arg0);
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_addProcedureCallback:
        {
          data.enforceInterface(descriptor);
          com.wizarpos.payment.aidl.IPaymentPayCallback _arg0;
          _arg0 = com.wizarpos.payment.aidl.IPaymentPayCallback.Stub.asInterface(data.readStrongBinder());
          this.addProcedureCallback(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_cancelRequest:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.cancelRequest(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_setParam:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _result = this.setParam(_arg0);
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.wizarpos.payment.aidl.IPaymentPay
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
          * execute transaction with json parameters
          * this is a synchronized method and it will be blocked until return the payment result
          * */
      @Override public java.lang.String transact(java.lang.String jsonData) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(jsonData);
          boolean _status = mRemote.transact(Stub.TRANSACTION_transact, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().transact(jsonData);
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* add callback during payment steps, used for prompt User procedure information
      	*/
      @Override public void addProcedureCallback(com.wizarpos.payment.aidl.IPaymentPayCallback callBack) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder((((callBack!=null))?(callBack.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_addProcedureCallback, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().addProcedureCallback(callBack);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
          * cancel previous request and will make transact return
          * this method return false when payment action can not be cancelled
          */
      @Override public boolean cancelRequest(java.lang.String jsonData) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(jsonData);
          boolean _status = mRemote.transact(Stub.TRANSACTION_cancelRequest, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().cancelRequest(jsonData);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.lang.String setParam(java.lang.String jsonData) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(jsonData);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setParam, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setParam(jsonData);
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static com.wizarpos.payment.aidl.IPaymentPay sDefaultImpl;
    }
    static final int TRANSACTION_transact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addProcedureCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_cancelRequest = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_setParam = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    public static boolean setDefaultImpl(com.wizarpos.payment.aidl.IPaymentPay impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.wizarpos.payment.aidl.IPaymentPay getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
      * execute transaction with json parameters
      * this is a synchronized method and it will be blocked until return the payment result
      * */
  public java.lang.String transact(java.lang.String jsonData) throws android.os.RemoteException;
  /**
  	* add callback during payment steps, used for prompt User procedure information
  	*/
  public void addProcedureCallback(com.wizarpos.payment.aidl.IPaymentPayCallback callBack) throws android.os.RemoteException;
  /**
      * cancel previous request and will make transact return
      * this method return false when payment action can not be cancelled
      */
  public boolean cancelRequest(java.lang.String jsonData) throws android.os.RemoteException;
  public java.lang.String setParam(java.lang.String jsonData) throws android.os.RemoteException;
}
