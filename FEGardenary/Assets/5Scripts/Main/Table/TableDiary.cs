using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using UnityEngine.UI;
using TMPro;


public class TableDiary : MonoBehaviour
{
    private CameraMovement cameraMovement;
    private GameObject targetObject;

    //å�� ������ �� �����ΰ�?
    private bool zoomFlag;


    //URI�� ��ū
    public string uri = "https://k7a604.p.ssafy.io/api/";
    public string token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxOCIsImlhdCI6MTY2NzU0OTI0NywiZXhwIjoxNjY3NTUxMDQ3fQ.DJahNxpkrHsDDL_-XR7034A_mAmBxx_9seZvjsJEFwc";

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            //CameraMovement ��ũ��Ʈ�� �����´�
            cameraMovement = GameObject.Find("Main Camera").GetComponent<CameraMovement>();
            //������ ������Ʈ�� ���������� ���� �Լ�
            targetObject = cameraMovement.GetClickedObject();
            //å�� ���� �Ǿ��� ���� ���� �˱� ���� ������ �����´�
            zoomFlag = cameraMovement.zoomFlag;

            //å�� ������ �Ǿ��� ����
            if (zoomFlag == true)
            {
                //Ŭ���� ���̾�� �� ���̾�� ���
                if (targetObject.name == "Flower Diary")
                {
                    Debug.Log("�� ���̾");
                    //�� ���̾ ��ü ����� �����ش�
                    GameObject.Find("TableUI").transform.Find("AllFlowerUI").gameObject.SetActive(true);
                                        
                }
                //Ŭ���� ���̾�� ���� ���̾�� ���
                else if (targetObject.name == "Tree Diary")
                {
                    //���� ���̾�� ���� �ٱ� UI(Canvas)�� Ȱ��ȭ
                    GameObject.Find("TableUI").transform.Find("AllTreeUI").gameObject.SetActive(true);
                    //�ۼ��� ���� ���̾ ������ ���� ǥ��
                    //�ƹ� �͵� �ۼ����� �ʾ��� ��
                    //if(treeDiary.responseDto.Count == 0)
                    //{
                        //�ۼ����� �ʾҴٴ� UI�� �����ش�
                    //}
                    //�� �� �ۼ��� �ϱⰡ ���� ��
                    /*else
                    {
                        
                    }*/
                }
            }
        }
    }
}
