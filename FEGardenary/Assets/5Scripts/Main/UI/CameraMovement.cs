using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraMovement : MonoBehaviour
{
    public GameObject Player;
    private GameObject targetObject;

    public float offsetX;
    public float offsetY = 45f;
    public float offsetZ = -40f;

    public Transform target;    // �� Ÿ��
    public float zoom;  // �� ��ġ
    // public float zoomSpeed;
    public bool zoomFlag;

    Vector3 cameraPosition;

    static public CameraMovement camera;

    private void Awake()
    {
        if (camera == null)
        {
            camera = this;
            DontDestroyOnLoad(gameObject);

        }
        else
        {
            Destroy(this.gameObject);

        }

    }

    void LateUpdate()
    {
        // å�� ����, �ܾƿ�
        if(zoomFlag)
        {
            cameraPosition.x = -7.47f;
            cameraPosition.y = 8.63f;
            cameraPosition.z = -20.5f;

            transform.position = cameraPosition;
            transform.rotation = Quaternion.Euler(48.26f, 0, 0);
        } else
        {
            cameraPosition.x = Player.transform.position.x + offsetX;
            cameraPosition.y = Player.transform.position.y + offsetY;
            cameraPosition.z = Player.transform.position.z + offsetZ;

            transform.position = cameraPosition;
            transform.rotation = Quaternion.Euler(26.346f, 0, 0);
        }

        // å������ ī�޶� ����
        if (Input.GetMouseButtonDown(0))
        {
            targetObject = GetClickedObject();

            if (targetObject != null && targetObject.name == "Table")
            {
                zoomFlag = true;
                
            } else if(targetObject != null && targetObject.name == "Floor")
            {
                zoomFlag = false;
            }
        }
    }

    // Ŭ���� ������Ʈ
    public GameObject GetClickedObject()
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
