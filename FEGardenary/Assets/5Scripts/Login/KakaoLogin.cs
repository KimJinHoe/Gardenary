using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net.Http;
using System;
using System.Net;
using Newtonsoft.Json;
using System.Text;

public class KakaoLogin : MonoBehaviour
{
    private AndroidJavaObject _androidJavaObject;
    private string uri = "https://k7a604.p.ssafy.io/api/";

    public void Show()
    {
        Debug.Log("click");
    }

    void Start()
    {
        _androidJavaObject = new AndroidJavaObject("com.DefaultCompany.FEGardenary.UKakao");

    }

    public void Login()
    {
        _androidJavaObject.Call("KakaoLogin");
    }

    // īī�� �α��� API
    public void getUserInfo(string kakaoId)
    {
        //Debug.Log("kakaoId : " + kakaoId);

        var client = new HttpClient();

        //body�� ���� ������
        var data = new KakaoId
        {
            kakaoId = kakaoId
        };
        var kakaoData = JsonConvert.SerializeObject(data);
        var requestKakaoId = new StringContent(kakaoData, Encoding.UTF8, "application/json");

        //Method�� Uri�� �����ϰ� Header�� ��ū�� �ִ´�
        var httpRequestMessage = new HttpRequestMessage
        {
            Method = HttpMethod.Post,
            RequestUri = new Uri(uri + "user/login"),
        };

        //���� body�� ���� �����͸� ���� ����ش�
        httpRequestMessage.Content = requestKakaoId;

        //API ��û�� �����ϰ� json���� ����� �޾ƿ´�
        var response = client.SendAsync(httpRequestMessage).Result;
        var json = response.Content.ReadAsStringAsync().Result;
        //json���� �޾ƿ� ����� string���� �ٲ۴�(?)
        //�����͸� ���� Ŭ������ �ϳ� ������ �Ѵ�
        ResKakao request = JsonConvert.DeserializeObject<ResKakao>(json);

        //Debug.Log("accessToken: " + request.responseDto.accessToken);
        //Debug.Log("nickname: " + request.responseDto.nickname);

        // �α��� ����
        if(request.status == "OK")
        {
            GameObject.Find("Login Panel").SetActive(false);
        }


    }

}
