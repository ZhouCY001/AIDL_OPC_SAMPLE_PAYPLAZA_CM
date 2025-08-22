package com.wizarpos.paymentrouterclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.wizarpos.payment.aidl.IPaymentPay;

import org.json.JSONException;
import org.json.JSONObject;

import static com.wizarpos.payment.aidl.GlobalAidlRequest.ActivateDevice;
import static com.wizarpos.payment.aidl.GlobalAidlRequest.AuthCancellation;
import static com.wizarpos.payment.aidl.GlobalAidlRequest.AuthCompletion;
import static com.wizarpos.payment.aidl.GlobalAidlRequest.GetPosInfo;
import static com.wizarpos.payment.aidl.GlobalAidlRequest.PreAuth;
import static com.wizarpos.payment.aidl.GlobalAidlRequest.PrintLast;
import static com.wizarpos.payment.aidl.GlobalAidlRequest.Purchase;
import static com.wizarpos.payment.aidl.GlobalAidlRequest.QueryTransaction;
import static com.wizarpos.payment.aidl.GlobalAidlRequest.Refund;

public class MainActivity extends Activity implements OnClickListener {

	private String param, response,criticalDate;
	boolean CMAppIconVisibility = true;
	String languageCode = "";

	private IPaymentPay mWizarPayment;
	final ServiceConnection mConnPayment = new PaymentConnection();

	String transAmount, oriTrace,oriTransDate,oriTransIndexCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		int[] btnIds = { R.id.bind, R.id.unbind
			, R.id.payCash, R.id.refund, R.id.getPOSInfo,R.id.printlast
			, R.id.preAuth, R.id.authComplete, R.id.authCancel
			, R.id.printByTrxid
			, R.id.actDevice,R.id.Language, R.id.setParam,
		};
		for (int id : btnIds) {
			findViewById(id).setOnClickListener(this);
		}

		Switch sw = (Switch) findViewById(R.id.switch_cm_visible);
		sw.setChecked(CMAppIconVisibility);
		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CMAppIconVisibility = isChecked;
			}
		});
	}

	@Override
	public void onBackPressed() {
		System.exit(0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindPaymentRouter();
	}

	class PaymentConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(ComponentName compName, IBinder binder) {
			Log.d("onServiceConnected", "compName: " + compName);
			mWizarPayment = IPaymentPay.Stub.asInterface(binder);
			showResponse("Connect Success!");
		}

		@Override
		public void onServiceDisconnected(ComponentName compName) {
			Log.d("onServiceDisconnected", "compName: " + compName);
			mWizarPayment = null;
			showResponse("Disconnect Success!");
		}
	};

	private void bindPaymentRouter() {
		if (mWizarPayment == null) {
			Intent intent = new Intent("com.wizarpos.payment.aidl.pay");
			intent.setPackage("com.wizarpos.opc");
			bindService(intent, mConnPayment, BIND_AUTO_CREATE);
		}
	}
	private void unbindPaymentRouter() {
		if (mWizarPayment != null) {
			unbindService(mConnPayment);
			mWizarPayment = null;
		}
	}

	public void showResponse(String response) {
		this.response = response;
		showResponse();
	}

	public void showResponse() {
		setTextById(R.id.param, param);
		setTextById(R.id.result, response);
	}
	private void setTextById(int id, CharSequence text) {
		((TextView)findViewById(id)).setText(text);
	}

	@Override
	public void onClick(final View view) {
		final int btnId = view.getId();
		setTextById(R.id.method, ((TextView)view).getText());

		param = "";
		response = "";
		criticalDate = "";
		switch(btnId) {
		case R.id.bind:				bindPaymentRouter();    break;
		case R.id.unbind:			unbindPaymentRouter();  break;
		default:
			if (mWizarPayment == null) {
				response = "Please click [ConnectPaymentRouter First]!";
				showResponse();
			} else {
				// 先获取输入，等输入完成后再执行transact
				handleTransactionWithInput(btnId);
			}
			break;
		}
	}

	private void handleTransactionWithInput(int btnId) {
		switch (btnId) {
		case R.id.actDevice:
			try {
				JSONObject json = new JSONObject();
				setParam4ActDevice(json);
				param = json.toString();
				createAsyncTask().execute(btnId);
			} catch (JSONException e) {
				e.printStackTrace();
				showResponse("JSON Error");
			}
			break;
		case R.id.payCash:
		case R.id.preAuth:
			showInputDialog("Input Trans Amount", 9, input -> {
				if (input == null) return; // 取消输入
				transAmount = input;
				try {
					JSONObject json = new JSONObject();
					if (btnId == R.id.preAuth)
						setParam4PreAuth(json);
					else
						setParam4PayCash(json);

					param = json.toString();
					createAsyncTask().execute(btnId);
				} catch (JSONException e) {
					e.printStackTrace();
					showResponse("JSON Error");
				}
			});
			break;
		case R.id.refund:
		case R.id.authComplete:
		case R.id.authCancel:
			showInputDialog("Input Trans Amount", 9, amount -> {
				if (amount == null) return;
				transAmount = amount;

				showInputDialog("Input oriTrace", 6, trace -> {
					if (trace == null) return;
					oriTrace = trace;

					showInputDialog("Input oriTransDate(yyyyMMdd)", 8, date -> {
						if (date == null) return;
						oriTransDate = date;

						try {
							JSONObject json = new JSONObject();
							if (btnId == R.id.refund)
								setparam4Refund(json);
							else if (btnId == R.id.authComplete)
								setparam4AuthComp(json);
							else if (btnId == R.id.authCancel)
								setparam4AuthCancel(json);

							param = json.toString();
							createAsyncTask().execute(btnId);
						} catch (JSONException e) {
							e.printStackTrace();
							showResponse("JSON Error");
						}
					});
				});
			});
			break;

		case R.id.printByTrxid:
			showInputDialog("Input oriTransIndexCode", 12, input -> {
				if (input == null) return;
				oriTransIndexCode = input;
				try {
					JSONObject json = new JSONObject();
					setParam4getPrintByTrxId(json);
					param = json.toString();
					createAsyncTask().execute(btnId);
				} catch (JSONException e) {
					e.printStackTrace();
					showResponse("JSON Error");
				}
			});
			break;

		case R.id.printlast:
		case R.id.getPOSInfo:
			try {
				JSONObject json = new JSONObject();
				if (btnId == R.id.printlast)
					setParam4getPrintLast(json);
				else
					setParam4getPOSInfo(json);

				param = json.toString();
				createAsyncTask().execute(btnId);
			} catch (JSONException e) {
				e.printStackTrace();
				showResponse("JSON Error");
			}
			break;
		case R.id.Language:
			showLanguageDialog();
			break;
		case R.id.setParam:
			try {
				JSONObject json = new JSONObject();
				setParam4setPaymentAPPParam(json);
				param = json.toString();
				createAsyncTask().execute(btnId);
			} catch (JSONException e) {
				e.printStackTrace();
				showResponse("JSON Error");
			}

			break;
		}
	}


	private AsyncTask<Integer, Void, String> createAsyncTask() {
		return new AsyncTask<Integer, Void, String>() {
			protected void onPreExecute() {
				showResponse("...");
			}
			protected String doInBackground(Integer...btnIds) {
				Log.d("doInBackground", "Request: " + param + " mWizarPayment: " + mWizarPayment);

				String result = "Skipped";
				try {
					switch(btnIds[0]) {
					case R.id.actDevice:
					case R.id.payCash:
					case R.id.refund:
					case R.id.printByTrxid:
					case R.id.preAuth:
					case R.id.authComplete:
					case R.id.authCancel:
					case R.id.printlast:
					case R.id.getPOSInfo:
						result = mWizarPayment.transact			(param);
						break;
					case R.id.setParam:
						result = mWizarPayment.setParam			(param);
						break;
					}
				} catch (RemoteException e) {
					result = e.getMessage();
				}

				Log.d("doInBackground", "Response: " + result);
				return result;
			}
			protected void onPostExecute(String result) {
				showResponse(result);
			}
		};
	}

	private void setParam4ActDevice(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", ActivateDevice);
		jsonObject.put("CallerName", "test merchant");
		jsonObject.put("TransIndexCode", "1234561");//Third application transaction order ID，This must be not repeated
		jsonObject.put("requestTips", "(AAAA BBBBB CCCC DDDD).  ");

	}

	private void setParam4PayCash(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", Purchase);
		jsonObject.put("CallerName", "test merchant");
		jsonObject.put("CurrencyCode", "978");
		jsonObject.put("TransIndexCode", "1234561");//Third application transaction order ID，This must be not repeated
		jsonObject.put("requestTips", "(AAAA BBBBB CCCC DDDD).  ");

		if(notEmptyString(transAmount))
			jsonObject.put("TransAmount", transAmount);
	}


	private void setparam4Refund(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", Refund);
		jsonObject.put("CallerName", "test merchant");
		jsonObject.put("CurrencyCode", "978");
		jsonObject.put("requestTips", "(AAAA BBBBB CCCC DDDD).  ");
		jsonObject.put("OriTraceNum", 000003);	//This value should be same of the 'trace' in Sale response data.

		if(notEmptyString(transAmount))
			jsonObject.put("TransAmount", transAmount);
		if(notEmptyString(oriTrace))
			jsonObject.put("OriTraceNum", oriTrace);	//This value should be same of the 'trace' in Sale response data.
		if(notEmptyString(oriTransDate))
			jsonObject.put("OriTransDate", oriTransDate);//Format: yyyyMMdd,This value must be same of the old transaction

	}

	private void setParam4PreAuth(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", PreAuth);
		jsonObject.put("CallerName", "test merchant");
		jsonObject.put("CurrencyCode", "978");
		jsonObject.put("TransIndexCode", "1234570");//Third application transaction order ID，This must be not repeated
		jsonObject.put("requestTips", "(AAAA BBBBB CCCC DDDD).  ");

		if(notEmptyString(transAmount))
			jsonObject.put("TransAmount", transAmount);
	}

	private void setparam4AuthComp(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", AuthCompletion);
		jsonObject.put("CallerName", "test merchant");
		jsonObject.put("CurrencyCode", "978");
		jsonObject.put("requestTips", "(AAAA BBBBB CCCC DDDD).  ");
		jsonObject.put("TransIndexCode", "1234571");//Third application transaction order ID，This must be not repeated
		if(notEmptyString(transAmount))
			jsonObject.put("TransAmount", transAmount);
		if(notEmptyString(oriTrace))
			jsonObject.put("OriTraceNum", oriTrace);	//This value should be same of the 'trace' in Sale response data.
		if(notEmptyString(oriTransDate))
			jsonObject.put("OriTransDate", oriTransDate);//Format: yyyyMMdd,This value must be same of the old transaction


	}

	private void setparam4AuthCancel(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", AuthCancellation);
		jsonObject.put("CallerName", "test merchant");
		jsonObject.put("CurrencyCode", "978");
		jsonObject.put("requestTips", "(AAAA BBBBB CCCC DDDD).  ");
		jsonObject.put("TransIndexCode", "1234572");//Third application transaction order ID，This must be not repeated

		if(notEmptyString(transAmount))
			jsonObject.put("TransAmount", transAmount);
		if(notEmptyString(oriTrace))
			jsonObject.put("OriTraceNum", oriTrace);	//This value should be same of the 'trace' in Sale response data.
		if(notEmptyString(oriTransDate))
			jsonObject.put("OriTransDate", oriTransDate);//Format: yyyyMMdd,This value must be same of the old transaction

	}



	private void setParam4getPOSInfo(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", GetPosInfo);
		jsonObject.put("CallerName", "test merchant");
	}

	private void setParam4getPrintLast(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", PrintLast);
		jsonObject.put("CallerName", "test merchant");
	}

	private void setParam4getPrintByTrxId(JSONObject jsonObject) throws JSONException {
		jsonObject.put("TransType", QueryTransaction);
		jsonObject.put("CallerName", "test merchant");

		if(notEmptyString(oriTransIndexCode))
			jsonObject.put("TransIndexCode", oriTransIndexCode);//Third application transaction order ID，This must be not repeated


	}

	private void setParam4setPaymentAPPParam(JSONObject jsonObject) throws JSONException {

		jsonObject.put("AppIconVisible", CMAppIconVisibility);
		if(!languageCode.isEmpty())
			jsonObject.put("LanguageCodes", languageCode);

	}

	public interface InputCallback {
		void onInput(String input);
	}

	private void showInputDialog(String title,int maxInput,InputCallback callback) {
		final EditText editText = new EditText(MainActivity.this);
		InputFilter[] filters = {new InputFilter.LengthFilter(maxInput)};
		editText.setFilters(filters);
		AlertDialog.Builder inputDialog =
			new AlertDialog.Builder(MainActivity.this);
		inputDialog.setTitle(title).setView(editText);
		inputDialog.setPositiveButton("Confirm", (dialog, which) -> {
			String text = editText.getText().toString();
			callback.onInput(text);
		});
		inputDialog.setNegativeButton("Cancel", (dialog, which) -> {
				callback.onInput(null); // 或其他取消逻辑
			});


		AlertDialog alertDialog = inputDialog.create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
	}


	private boolean notEmptyString(String str){
		if(str!=null && !str.isEmpty())
			return true;

		return false;
	}

	public void showLanguageDialog() {
		final String[] languages = {"English", "Français", "Nederlands", "Español"};
		final String[] tags = {"en", "fr", "nl", "es"};

		new AlertDialog.Builder(MainActivity.this)
			.setTitle("Select Language")
			.setItems(languages, (dialog, which) -> {
				languageCode = tags[which];
				if(notEmptyString(languageCode)){
					Button btn = (Button) findViewById(R.id.Language);
					btn.setText(languageCode);
				}
			})
			.setNegativeButton("Cancel", null)
			.show();
	}
}


