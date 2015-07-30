==========================================================================
          ePOS-Print SDK for Android  Version 1.6.0

          Copyright Seiko Epson Corporation 2012-2014 All rights reserved.
==========================================================================

1.�{�\�t�g�E�F�A�ɂ���

ePOS-Print SDK for Android �́AEPSON TM�v�����^�[�Ɉ�����邽�߂�
Android�A�v���P�[�V�������J������A�J���Ҍ���SDK�ł��B
ePOS-Print SDK �Œ񋟂���API���g�p���ăA�v���P�[�V�������J�����܂��B
ePOS-Print SDK �ɂ́AiOS�f�o�C�X������ ePOS-Print SDK for iOS ��
�p�ӂ���Ă��܂��B
�ڍׂ� ePOS-Print SDK for Android ���[�U�[�Y�}�j���A�� ���Q�Ƃ��������B

�Ή�Android�o�[�W����
  Android 2.3.3 - 2.3.7
  Android 3.1 - 3.2.2
  Android 4.0 - 4.4

�Ή�Android�[��
  ARMv5TE �Ή��[��

�T�|�[�g�v�����^�[
  EPSON TM-T88V
  EPSON TM-T70
  EPSON TM-T70II
  EPSON TM-T20�i�C�O���f���̂݁j
  EPSON TM-T82�i�C�O���f���̂݁j
  EPSON TM-T81II�i�C�O���f���̂݁j
  EPSON TM-T82II�i�C�O���f���̂݁j
  EPSON TM-U220 �V���[�Y�i�C�O���f���̂݁j
  EPSON TM-P60�i�C�O���f���̂݁j
  EPSON TM-P60II
  EPSON TM-T90II
  EPSON TM-T20II
  EPSON TM-P80�i�C�O���f���̂݁j
  EPSON TM-U330 �V���[�Y�i�C�O���f���̂݁j
  EPSON TM-T83II�i�C�O���f���̂݁j
  EPSON TM-P20

�T�|�[�g�C���^�[�t�F�C�X
  �L��LAN
  ����LAN
  Bluetooth
  USB


2.�񋟃t�@�C��

�EePOS-Print.jar
  API��Java�v���O�������痘�p���邽�߂�jar�`���t�@�C���ɃA�[�J�C�u���ꂽ
  �R���p�C���ς�Java�̃N���X�t�@�C���ł��B
�EePOSEasySelect.jar
  �ȒP�Ƀv�����^�[��I�����邽�߂� Java �̃N���X�t�@�C���ł��B
�Elibeposprint.so
  �@�\���s�p���C�u�����[�ł��B�iARMv5TE �ɑΉ��j
�EePOS-Print_Sample_Android.zip
  �T���v���v���O�����t�@�C���ł��B
�EEULA.jp.txt
  SOFTWARE LICENSE AGREEMENT ���L�ڂ���Ă��܂��B
�EEULA.en.txt
  SOFTWARE LICENSE AGREEMENT�i�p��Łj���L�ڂ���Ă��܂��B
�EePOS-Print_SDK_Android_ja_revK.pdf
  ���[�U�[�Y�}�j���A���ł��B
�EePOS-Print_SDK_Android_en_revK.pdf
  ���[�U�[�Y�}�j���A���i�p��Łj�ł��B
�EePOS-Print_SDK_Android_AppDevGuide_J_RevB.pdf
  �J���҃K�C�h�ł��B
�EePOS-Print_SDK_Android_AppDevGuide_E_RevB.pdf
  �J���҃K�C�h�i�p��Łj�ł��B
�EREADME.jp.txt
  �{���ł��B
�EREADME.en.txt
  �{���i�p��Łj�ł��B


3.���̑����ӓ_

�E�g�p���@�A�g�p��̒��ӁA���̏ڍׂ́A���[�U�[�Y�}�j���A�����Q�Ƃ��A
  ���g�p���������B
�E�C���^�[�t�F�C�X��USB�̏ꍇ�A���O�ɃA�v���P�[�V������USB�f�o�C�X�ւ̃A�N�Z�X�����擾���邱�Ƃ𐄏����܂��B
  �A�v���P�[�V�����ŃA�N�Z�X�����擾������@���A�ȉ��ɋL���܂��B
   1. AndroidManifest.xml �Ɉȉ��̃R�[�h��ǋL���܂��B
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
   2. ���\�[�X�t�@�C���� res/xml/device_filter.xml ��ǉ����Adevice_filter.xml �t�@�C���Ɉȉ��̃R�[�h���L�q���܂��B
      <?xml version="1.0" encoding="utf-8"?>  
      <resources>  
          <usb-device vendor-id="1208" />
      </resources>
  �A�N�Z�X�����擾����ۂɂ́A�_�C�A���O���\�������̂�OK�{�^����I�����Ă��������B

  �܂����O��USB�f�o�C�X�ւ̃A�N�Z�X�����擾�����AopenPrinter���\�b�h�ɂ��|�[�g�I�[�v������ۂɂ�
  �ȉ��̒��ӓ_������܂��B
    �E�A�N�Z�X���擾�̃_�C�A���O�ŁAOK�{�^����I������ƃ|�[�g�I�[�v����10�b�O��̎��Ԃ��|����܂��B
    �E�A�N�Z�X���擾�̃_�C�A���O�ŁA�L�����Z���{�^����I�������30�b�̃^�C���A�E�g�҂��ɂȂ�܂��B


4.���o�[�W��������̕ύX�_

  Version 1.6.0
    �E�T�|�[�g�v�����^�[��ǉ��B
      �ETM-P20
    �E�f�o�C�X���擾�p�̌���API��ǉ��B
    �EopenPrinter(�^�C���A�E�g���Ԑݒ�p) API��ǉ��B
    �EopenPrinter API�̋@�\���P�B
      �ETCP�ڑ�����deviceName�ɁAMAC�A�h���X�A�z�X�g�����w��ł���悤�ɉ��P�B
    �E���O�̋@�\���P�B
      �E�o�b�N�A�b�v�t�@�C���̈��k�Ή�
      �E�o�͓��e�̌�����
    �E�d�l�ύX�B
      �ETCP�ڑ����A���ɑ��̒[������|�[�g�I�[�v������Ă�����A�w�莞�Ԍo�ߌ�G���[��Ԃ��悤�ɕύX�B

  Version 1.5.0
    �E�T�|�[�g�v�����^�[��ǉ��B
      �ETM-U330
      �ETM-T83II
    �E���k�C���[�W�����p�̃R�}���h����API��ǉ��B

  Version 1.4.2
    �E�s��C���B
      �EsendData���\�b�h���s���̃X�e�[�^�X���������Ȃ����Ƃ�����

  Version 1.4.1
    �E�s��C���B
      �E�o�b�e���[�񓋍ڂ̃v�����^�[��openPrinter���\�b�h�AsendData���\�b�h��
        ���s����ƁA��1�b�̒x������������B

  Version 1.4.0
    �E�T�|�[�g�C���^�[�t�F�C�X��ǉ��B
      �EUSB
    �EStatusMonitor�̋@�\���P�B
      �E�X�e�[�^�X�擾���쎞�̃��C�u���������ʐM���������P�B
    �E�s��C���B
      �ETM-P80�̃T�|�[�gAPI��addCut, addFeedPosition, addLayout API��ǉ��B

  Version 1.3.4a
    �E�Ή�Android�o�[�W������ǉ��B
      �EAndroid 4.4

  Version 1.3.4
    �E�T�|�[�g�v�����^�[��ǉ��B
      �ETM-T20II
    �E�s��C���B
      �E�v�����^�[�X�e�[�^�X���Ď����ɁAclosePrinter���\�b�h��
        ���s����Ǝ��s����ꍇ������B
      �EBluetooth�ڑ��g�p���A�I�t���C����Ԃ�openPrinter���\�b�h��
        ���s����Ɛ���ȃv�����^�[�X�e�[�^�X���擾�ł��Ȃ��B

  Version 1.3.3
    �E�e�L�X�g�󎚂̑��x���P�B

  Version 1.3.2
    �E�T�|�[�g�v�����^�[��ǉ��B
      �ETM-T20II
      �ETM-P80

  Version 1.3.1
    �E�T�|�[�g�v�����^�[��ǉ��B
      �ETM-T90II
    �E�Ή�Android�o�[�W������ǉ��B
      �EAndroid 4.2.2

  Version 1.3.0
    �E���O�o�͋@�\��ǉ��B
    �E�T�|�[�g�v�����^�[��ǉ��B
      �ETM-T70II
    �E�Ή�Android�o�[�W������ǉ��B
      �EAndroid 4.2.1

  Version 1.2.1
    �E�s��C���B

  Version 1.2.0
    �E���x������p�̃R�}���h����API��ǉ��B
    �E�p�����C�A�E�g��ݒ肷�邽�߂̃R�}���h����API��ǉ��B
    �E�T�|�[�g�v�����^�[��ǉ��B
      �ETM-T82II

  Version 1.1.0
    �E�X�e�[�^�X�C�x���g�ʒm�@�\��ǉ��B
    �E���X�^�[�C���[�W�n�[�t�g�[���������@��ǉ��B
    �E���K������ɑΉ��B
    �E�Ή������ǉ��B
      �E�ȑ̎�������
      �E�ɑ̎�������
      �E�؍���
      �E�^�C��
      �E�x�g�i����
    �E�o�b�e���[�X�e�[�^�X�擾�E�ʒm�@�\�ǉ��B
    �E�O���t�B�b�N�󎚂̃p�t�H�[�}���X�����P�B
    �E�T�|�[�g�v�����^�[��ǉ��B
      �ETM-T20
      �ETM-T82
      �ETM-T81II
      �ETM-P60II
      �ETM-P60 (Wireless LAN)

  Version 1.0.0
    �E�V�K�����[�X�B
