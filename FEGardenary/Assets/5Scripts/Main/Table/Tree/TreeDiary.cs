using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net.Http;
using System;
using System.Net;
using Newtonsoft.Json;
using System.Text;
using TMPro;

public class TreeDiary : MonoBehaviour
{
    //���� ���̾�� ����� ������ ������Ʈ
    GameObject treeContentPrefab;

    //�츮 ���� uri�� ����� ��ū(���Ƿ� ���� ��ū)
    private string uri = "https://k7a604.p.ssafy.io/api/";
    private string token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNzA3IiwiaWF0IjoxNjY3ODc5MDIwLCJleHAiOjE2Njc4OTcwMjB9.u0iqTN94P6l7W8loEMEOuxcIWxaiW8EMg8yyqRdvFjs";
    
    //api ��û �� response ����� ���� Ŭ����
    public ResTreeDiary TreeDiaryList;

    void Start()
    {
        //���� ���̾ ��ȸ API
        //��û�� ������ ���� client
        var client = new HttpClient();

        //body�� ���� ������
        var dateCreate = new ReqTreeDiary
        {
            //������ ������ ��¥�� ������ �����ؾ���(������ ���Ƿ� ���� ��)
            date = "2022-11-08T09:54:01.242012"
        };
        var treeDate = JsonConvert.SerializeObject(dateCreate);
        var requestTreeDate = new StringContent(treeDate, Encoding.UTF8, "application/json");

        //Method�� Uri�� �����ϰ� Header�� ��ū�� �ִ´�
        var httpRequestMessage = new HttpRequestMessage
        {
            Method = HttpMethod.Post,
            RequestUri = new Uri(uri + "tree/diary/date"),
            Headers =
            {
                { HttpRequestHeader.Authorization.ToString(), "Bearer " + token }
            },
        };

        //���� body�� ���� �����͸� ���� ����ش�
        httpRequestMessage.Content = requestTreeDate;
       
        //API ��û�� �����ϰ� json���� ����� �޾ƿ´�
        var response = client.SendAsync(httpRequestMessage).Result;
        var json = response.Content.ReadAsStringAsync().Result;
        //json���� �޾ƿ� ����� string���� �ٲ۴�(?)
        //�����͸� ���� Ŭ������ �ϳ� ������ �Ѵ�
        TreeDiaryList = JsonConvert.DeserializeObject<ResTreeDiary>(json);
        Debug.Log(TreeDiaryList.responseDto.Count);

        CreateTreeContent();
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    //���� ���̾�� Ư�� ��¥�� ����� �����ִ� �Լ�
    void CreateTreeContent()
    {
        //treeContentPrefab = Resources.Load("TreeContent") as GameObject;
        for(int i = 0; i < TreeDiaryList.responseDto.Count; i++)
        {
            treeContentPrefab = Resources.Load("TreeContentPrefab") as GameObject;
            treeContentPrefab.transform.Find("TreeContentText").GetComponentInChildren<TextMeshProUGUI>().text
                = TreeDiaryList.responseDto[i].content;
            //treeContentPrefab.transform.SetParent(GameObject.Find("TreeViewport").transform.Find("TreeContent"));
            Instantiate(Resources.Load<GameObject>("TreeContentPrefab"),
                GameObject.Find("TreeViewport").transform.Find("TreeContent"));
        }
    }
}
