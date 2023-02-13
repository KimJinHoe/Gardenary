using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class Flower : MonoBehaviour
{
    //��, ���� ���̾ �ۼ��� ���ӵ� �Ⱓ ǥ�� UI
    public TextMeshProUGUI flowerPeriod, treePeriod;

    //���� �� ��° ��������, ���� ���� ǥ�� UI
    public TextMeshProUGUI questionUI, questionNumUI;

    //��, ������ ���� ����ġ
    private int flowerExp, treeExp;

    //��, ���� ���̾ �ۼ��� ���ӵ� �Ⱓ
    private int flowerNum, treeNum;

    //�� ������ ���°����
    private int questionNum;
    //�� ������ ��������
    private string question;

    //�� ���̾ �ۼ�
    public TMP_InputField flowerInput;
    private string flowerAnswer;

    //���� ���̾ �ۼ�
    public TMP_InputField treeInput;
    private string treeText;

    //�ϼ��� ���� ��������
    private string flowerName;

    //�� ���̾ �ۼ� �� ����ġ
    private int FlowerAllExp, TreeAllExp;

    //������ ȹ�� ����
    private bool FlowerItemFlag, TreeItemFlag;

    //ĳ���� ȹ�� ����
    private int characterFlag;

    private WriteDiary writeDiary;

    private Transform check;

    // Start is called before the first frame update
    void Start()
    {
        //�� ����ġ, ���� ����, �� ��° ����, ���� ��ĥ �Ǿ������� ���� ������ �ٸ� ��ũ��Ʈ���� ��������
        writeDiary = GameObject.Find("FlowerGroup").GetComponent<WriteDiary>();
        flowerExp = writeDiary.flowerExp;
        question = writeDiary.questionContent;
        questionNum = writeDiary.questionNum;
        flowerNum = writeDiary.flowerNum;

        //�� ���� ��ĥ �ۼ�?
        flowerPeriod = GameObject.Find("FlowerHeader").transform.Find("FlowerDate").GetComponentInChildren<TextMeshProUGUI>();
        flowerPeriod.text = "���� " + flowerNum + "��";

        //�� ��° ����?
        questionNumUI = GameObject.Find("QuestionBox").transform.Find("QuestionNumber").GetComponentInChildren<TextMeshProUGUI>();
        questionNumUI.text = "# " + questionNum + "��° ����";

        //���� ����
        questionUI = GameObject.Find("QuestionBox").transform.Find("Question").GetComponentInChildren<TextMeshProUGUI>();
        questionUI.text = question;
        //������ ����ġ�� ���������� �ۼ��� �Ⱓ�� ���� ���� �ٸ� ��ũ��Ʈ���� ��������
        treeExp = writeDiary.treeExp;
        treeNum = writeDiary.treeNum;

        //���� ���� ��ĥ �ۼ�?
        treePeriod = GameObject.Find("TreeHeader").transform.Find("TreeDate").GetComponentInChildren<TextMeshProUGUI>();
        treePeriod.text = "���� " + treeNum + "��";
    }

    // Update is called once per frame
    void Update()
    {

    }

    //�� ���̾ �ۼ� ��ư Ŭ��
    public void FlowerWrite()
    {
        //�ۼ��� �亯
        flowerAnswer = flowerInput.text;
        //�ۼ��� ������ ���� ��
        if (flowerAnswer.Length == 0)
        {
            //�ۼ� �Ϸ� UI ǥ��
            GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
            GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
            //���ڸ� �ۼ��ش޶�� ���� ���
            GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "���ڸ� �ۼ����ּ���! :(";
        }
        //�ۼ��� ������ ���� ��
        else if (flowerAnswer.Length != 0)
        {
            //�� ���̾ �ۼ� API ȣ��
            //�ۼ� �� �� ����ġ�� ������ ȹ�� ���θ� ����
            FlowerAllExp = 100;
            FlowerItemFlag = true;

            if (FlowerAllExp == 100)
            {

                //�� �ϼ� UI ǥ��
                GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
                GameObject.Find("WriteComplete").transform.Find("CompletePopup").gameObject.SetActive(true);
                //������ ȹ��� ĳ���� ȹ�� üũ
                check = GameObject.Find("CompletePopup").transform.Find("CompleteButton");
            }
            else
            {
                //�ۼ� �Ϸ� UI ǥ��
                GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
                GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
                //�ۼ� �Ϸ� ���� ���
                GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "�ۼ��� �Ϸ�Ǿ����ϴ�! :)";//������ ȹ��� ĳ���� ȹ�� üũ
                //������ ȹ��� ĳ���� ȹ�� üũ
                check = GameObject.Find("WritePopup").transform.Find("PopupExitButton");
            }
            
            check.GetComponent<Button>().onClick.AddListener(() => GetItem(FlowerItemFlag));
            check.GetComponent<Button>().onClick.AddListener(() => GetCharacter(FlowerAllExp));
        }
    }

    //���� ���̾ �ۼ� ��ư Ŭ��
    public void TreeWrite()
    {
        //�ۼ��� ����
        treeText = treeInput.text;
        //�ۼ��� ������ ���� ��
        if (treeText.Length == 0)
        {
            //�ۼ� �Ϸ� UI ǥ��
            GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
            GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
            //���ڸ� �ۼ��ش޶�� ���� ���
            GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "���ڸ� �ۼ����ּ���! :(";
        }
        //���� ��
        else
        {
            //���� ���̾ �ۼ� API ȣ��
            //�ۼ� �� �� ����ġ�� ������ ȹ�� ���θ� ����
            TreeAllExp = 100;
            TreeItemFlag = true;

            if (TreeAllExp == 100)
            {
                //���� �ϼ� UI ǥ��
                GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
                GameObject.Find("WriteComplete").transform.Find("CompletePopup").gameObject.SetActive(true);
                //������ ȹ��� ĳ���� ȹ�� üũ
                check = GameObject.Find("CompletePopup").transform.Find("CompleteButton");
            }
            else if (TreeAllExp < 100)
            {
                //�ۼ� �Ϸ� UI ǥ��
                GameObject.Find("FarmUI").transform.Find("WriteComplete").gameObject.SetActive(true);
                GameObject.Find("WriteComplete").transform.Find("WritePopup").gameObject.SetActive(true);
                //�ۼ� �Ϸ� ���� ���
                GameObject.Find("WritePopup").transform.Find("PopupText").GetComponentInChildren<TextMeshProUGUI>().text = "�ۼ��� �Ϸ�Ǿ����ϴ�! :)";
                check = GameObject.Find("WritePopup").transform.Find("PopupExitButton");
            }
            //������ ȹ��� ĳ���� ȹ�� üũ
            check.GetComponent<Button>().onClick.AddListener(() => GetItem(TreeItemFlag));
            check.GetComponent<Button>().onClick.AddListener(() => GetCharacter(TreeAllExp));

            //���� ���̾ �Է� �ʱ�ȭ
            treeInput.Select();
            treeInput.text= "";
        }
    }

    //������ ȹ��
    public void GetItem(bool itemFlag)
    {
        if (itemFlag == true)
        {
            //������ ȹ�� API ȣ�� -> ������ ���� ���̵�, �̸�
            GameObject.Find("FarmUI").transform.Find("GetItem").gameObject.SetActive(true);
        }
    }


    //ĳ���� ȹ��
    public void GetCharacter(int allExp)
    {
        //�ϼ����� ��� ĳ���� API ȣ��
        if (allExp == 100)
        {
            //ĳ���� ���̵� ���̳�
            characterFlag = 1;
            //��ȯ�� ĳ���� ���̵� ���� 0�� ���� ���ο� ĳ���͸� ���� ���� ��
            if (characterFlag != 0)
            {
                //���ο� ĳ���͸� ����ٴ� UI ǥ��
                GameObject.Find("FarmUI").transform.Find("GetCharacter").gameObject.SetActive(true);
            }
        }
    }

}
