using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class WriteDiary : MonoBehaviour
{
    //�� ����ġ, ���� ����ġ, ���� �� �ۼ� ����, ���� ����, �� ��° ����, �� ���� ��¥, ���� ���� ��¥
    public int flowerExp;
    public int treeExp;
    private bool flowerFlag;
    public string questionContent;
    public int questionNum;
    public int flowerNum;
    public int treeNum;

    public GameObject targetObject;

    // Start is called before the first frame update
    void Start()
    {
        //�Թ� ��ȸ API ����
        flowerExp = 10;
        treeExp = 20;
        flowerFlag = true;
        questionContent = "���� �Ϸ� ���� ��￡ ���� ���� �����ΰ���?";
        questionNum = 14;
        flowerNum = 5;
        treeNum = 7;

        //����ġ�� ���� �ٸ� ���� ������� �Ѵ�
        if (flowerExp <= 20)
        {
            GameObject.Find("FlowerGroup").transform.Find("Flower1").gameObject.SetActive(true);
        }
        else if(flowerExp <= 40)
        {
            GameObject.Find("FlowerGroup").transform.Find("Flower2").gameObject.SetActive(true);
        }
        else if(flowerExp <= 70)
        {

        }
        else if(flowerExp < 100)
        {

        }
        
        //����ġ�� ���� �ٸ� ������ ������� �Ѵ�
        if(treeExp <= 20)
        {
            GameObject.Find("TreeGroup").transform.Find("Tree1").gameObject.SetActive(true);
        }
        else if (treeExp <= 40)
        {
            
        }
        else if (treeExp <= 70)
        {

        }
        else if (treeExp < 100)
        {

        }
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            targetObject = GetClickedObject();
            //Ŭ���� ������Ʈ�� ���� ��
            if(targetObject.name == "Flower1" || targetObject.name == "Flower2" ||
                targetObject.name == "Flower3" || targetObject.name == "Flower4")
            {
                //���� ������ �� ���̾�� �ۼ����� �ʾ��� ��
                if(flowerFlag == true)
                {
                    //�� ���̾ �ۼ� UI�� ������!
                    GameObject.Find("FlowerUI").transform.Find("FlowerWrite").gameObject.SetActive(true);
                }
                //�̹� �ۼ����� ��
                else if(flowerFlag == false || GameObject.Find("FarmUI").transform.Find("FlowerUI").gameObject.activeSelf == false)
                {
                    //�̹� �ۼ��ߴٴ� UI�� ������!
                    GameObject.Find("FarmUI").transform.Find("AlreadyWrite").gameObject.SetActive(true);
                }
            }
            //Ŭ���� ������Ʈ�� ������ ��
            else if(targetObject.name == "Tree1" || targetObject.name == "Tree2" ||
                targetObject.name == "Tree3" || targetObject.name == "Tree4")
            {
                //���� ���̾ �ۼ� UI�� ������!
                if(GameObject.Find("FarmUI").transform.Find("TreeUI").gameObject.activeSelf == false)
                {
                    GameObject.Find("FarmUI").transform.Find("TreeUI").gameObject.SetActive(true);
                }
                GameObject.Find("TreeUI").transform.Find("TreeWrite").gameObject.SetActive(true);
            }
        }
    }

    //Ŭ���� ������Ʈ�� � ��
    GameObject GetClickedObject()
    {

        RaycastHit hit; // �浹�� ������ ����
        GameObject target = null;

        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);    // ���콺 ����Ʈ ��ó ��ǥ ����

        // ���콺 ��ó�� ������Ʈ�� �ִ��� Ȯ��
        if (Physics.Raycast(ray.origin, ray.direction * 10, out hit))
        {
            target = hit.collider.gameObject;   // �ش� ������Ʈ ����
        }

        return target;
    }
}
