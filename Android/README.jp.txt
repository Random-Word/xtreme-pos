==========================================================================
          ePOS-Print SDK for Android  Version 1.6.0

          Copyright Seiko Epson Corporation 2012-2014 All rights reserved.
==========================================================================

1.本ソフトウェアについて

ePOS-Print SDK for Android は、EPSON TMプリンターに印刷するための
Androidアプリケーションを開発する、開発者向けSDKです。
ePOS-Print SDK で提供するAPIを使用してアプリケーションを開発します。
ePOS-Print SDK には、iOSデバイス向けの ePOS-Print SDK for iOS も
用意されています。
詳細は ePOS-Print SDK for Android ユーザーズマニュアル を参照ください。

対応Androidバージョン
  Android 2.3.3 - 2.3.7
  Android 3.1 - 3.2.2
  Android 4.0 - 4.4

対応Android端末
  ARMv5TE 対応端末

サポートプリンター
  EPSON TM-T88V
  EPSON TM-T70
  EPSON TM-T70II
  EPSON TM-T20（海外モデルのみ）
  EPSON TM-T82（海外モデルのみ）
  EPSON TM-T81II（海外モデルのみ）
  EPSON TM-T82II（海外モデルのみ）
  EPSON TM-U220 シリーズ（海外モデルのみ）
  EPSON TM-P60（海外モデルのみ）
  EPSON TM-P60II
  EPSON TM-T90II
  EPSON TM-T20II
  EPSON TM-P80（海外モデルのみ）
  EPSON TM-U330 シリーズ（海外モデルのみ）
  EPSON TM-T83II（海外モデルのみ）
  EPSON TM-P20

サポートインターフェイス
  有線LAN
  無線LAN
  Bluetooth
  USB


2.提供ファイル

・ePOS-Print.jar
  APIをJavaプログラムから利用するためのjar形式ファイルにアーカイブされた
  コンパイル済みJavaのクラスファイルです。
・ePOSEasySelect.jar
  簡単にプリンターを選択するための Java のクラスファイルです。
・libeposprint.so
  機能実行用ライブラリーです。（ARMv5TE に対応）
・ePOS-Print_Sample_Android.zip
  サンプルプログラムファイルです。
・EULA.jp.txt
  SOFTWARE LICENSE AGREEMENT が記載されています。
・EULA.en.txt
  SOFTWARE LICENSE AGREEMENT（英語版）が記載されています。
・ePOS-Print_SDK_Android_ja_revK.pdf
  ユーザーズマニュアルです。
・ePOS-Print_SDK_Android_en_revK.pdf
  ユーザーズマニュアル（英語版）です。
・ePOS-Print_SDK_Android_AppDevGuide_J_RevB.pdf
  開発者ガイドです。
・ePOS-Print_SDK_Android_AppDevGuide_E_RevB.pdf
  開発者ガイド（英語版）です。
・README.jp.txt
  本書です。
・README.en.txt
  本書（英語版）です。


3.その他留意点

・使用方法、使用上の注意、等の詳細は、ユーザーズマニュアルを参照し、
  ご使用ください。
・インターフェイスがUSBの場合、事前にアプリケーションでUSBデバイスへのアクセス許可を取得することを推奨します。
  アプリケーションでアクセス許可を取得する方法を、以下に記します。
   1. AndroidManifest.xml に以下のコードを追記します。
      <manifest ...>
          <application>
              <activity ...>
                  <intent-filter>
                      <action android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED" />
                  </intent-filter>
                  <meta-data android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED"
                    android:resource="@xml/device_filter" />
              </activity>
          </application>
      </manifest>
   2. リソースファイルに res/xml/device_filter.xml を追加し、device_filter.xml ファイルに以下のコードを記述します。
      <?xml version="1.0" encoding="utf-8"?>  
      <resources>  
          <usb-device vendor-id="1208" />
      </resources>
  アクセス許可を取得する際には、ダイアログが表示されるのでOKボタンを選択してください。

  また事前にUSBデバイスへのアクセス許可を取得せず、openPrinterメソッドによりポートオープンする際には
  以下の注意点があります。
    ・アクセス許可取得のダイアログで、OKボタンを選択するとポートオープンに10秒前後の時間が掛かります。
    ・アクセス許可取得のダイアログで、キャンセルボタンを選択すると30秒のタイムアウト待ちになります。


4.旧バージョンからの変更点

  Version 1.6.0
    ・サポートプリンターを追加。
      ・TM-P20
    ・デバイス名取得用の検索APIを追加。
    ・openPrinter(タイムアウト時間設定用) APIを追加。
    ・openPrinter APIの機能改善。
      ・TCP接続時のdeviceNameに、MACアドレス、ホスト名を指定できるように改善。
    ・ログの機能改善。
      ・バックアップファイルの圧縮対応
      ・出力内容の見直し
    ・仕様変更。
      ・TCP接続時、既に他の端末からポートオープンされていたら、指定時間経過後エラーを返すように変更。

  Version 1.5.0
    ・サポートプリンターを追加。
      ・TM-U330
      ・TM-T83II
    ・圧縮イメージ処理用のコマンド生成APIを追加。

  Version 1.4.2
    ・不具合修正。
      ・sendDataメソッド実行時のステータスが正しくないことがある

  Version 1.4.1
    ・不具合修正。
      ・バッテリー非搭載のプリンターでopenPrinterメソッド、sendDataメソッドを
        実行すると、約1秒の遅延が発生する。

  Version 1.4.0
    ・サポートインターフェイスを追加。
      ・USB
    ・StatusMonitorの機能改善。
      ・ステータス取得動作時のライブラリ内部通信効率を改善。
    ・不具合修正。
      ・TM-P80のサポートAPIにaddCut, addFeedPosition, addLayout APIを追加。

  Version 1.3.4a
    ・対応Androidバージョンを追加。
      ・Android 4.4

  Version 1.3.4
    ・サポートプリンターを追加。
      ・TM-T20II
    ・不具合修正。
      ・プリンターステータスを監視中に、closePrinterメソッドを
        実行すると失敗する場合がある。
      ・Bluetooth接続使用時、オフライン状態でopenPrinterメソッドを
        実行すると正常なプリンターステータスを取得できない。

  Version 1.3.3
    ・テキスト印字の速度改善。

  Version 1.3.2
    ・サポートプリンターを追加。
      ・TM-T20II
      ・TM-P80

  Version 1.3.1
    ・サポートプリンターを追加。
      ・TM-T90II
    ・対応Androidバージョンを追加。
      ・Android 4.2.2

  Version 1.3.0
    ・ログ出力機能を追加。
    ・サポートプリンターを追加。
      ・TM-T70II
    ・対応Androidバージョンを追加。
      ・Android 4.2.1

  Version 1.2.1
    ・不具合修正。

  Version 1.2.0
    ・ラベル制御用のコマンド生成APIを追加。
    ・用紙レイアウトを設定するためのコマンド生成APIを追加。
    ・サポートプリンターを追加。
      ・TM-T82II

  Version 1.1.0
    ・ステータスイベント通知機能を追加。
    ・ラスターイメージハーフトーン処理方法を追加。
    ・多階調印刷に対応。
    ・対応言語を追加。
      ・簡体字中国語
      ・繁体字中国語
      ・韓国語
      ・タイ語
      ・ベトナム語
    ・バッテリーステータス取得・通知機能追加。
    ・グラフィック印字のパフォーマンスを改善。
    ・サポートプリンターを追加。
      ・TM-T20
      ・TM-T82
      ・TM-T81II
      ・TM-P60II
      ・TM-P60 (Wireless LAN)

  Version 1.0.0
    ・新規リリース。
