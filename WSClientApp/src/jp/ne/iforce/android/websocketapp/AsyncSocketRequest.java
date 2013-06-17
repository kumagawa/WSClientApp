package jp.ne.iforce.android.websocketapp;

import de.roderick.weberknecht.WebSocketEventHandler;
import de.roderick.weberknecht.WebSocketMessage;
import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


public class AsyncSocketRequest extends AsyncTask<Uri.Builder, Void, String> {
    private Context xContext;
    private Handler mainHandler;

    public AsyncSocketRequest(Context context) {
    	xContext = context;
    	mainHandler = new Handler();
    }

    @Override
    protected void onPreExecute() {
    }


	@Override
	protected String doInBackground(Builder... params) {
		WebSocketManager.connect("ws://192.168.1.112:8080/ws", new WebSocketEventHandler() { //接続開始時の処理.
			public void onOpen() {
				mainHandler.post(new Tes("websocket connect open",xContext));
				Log.d("DEBUG", "websocket connect open");
			}

			@Override
			public void onMessage(final WebSocketMessage message) { // メッセージ受信時の処理
				mainHandler.post(new Tes(message.getText(),xContext));
				Log.d("DEBUG", "message:" + message.getText());
			}

			//接続終了時の処理.
			public void onClose() {
				mainHandler.post(new Tes("websocket connect close",xContext));
				Log.d("DEBUG", "websocket connect close");
			}
		});

		//送信.
		//WebSocketManager.send("check");
		return null;
	}

	class Tes implements Runnable{

		private String message;
		private Context con;
		public Tes(String message,Context con){
			this.message = message;
			this.con = con;
		}

		@Override
		public void run() {
			Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
		}

	}
}