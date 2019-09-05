package com.yuwei.grpc;

import com.yuwei.proto.*;
import io.grpc.stub.StreamObserver;

/**
 * Created:  Y.w
 * Date: 2019-09-04
 * Time: 11:01
 * Description:
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端信息：" + request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到客户端信息：" + request.getAge());

        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(30).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(40).setCity("杭州").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("赵六").setAge(50).setCity("湖州").build());

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            //接收到客户端的消息，就调用一次，知道客户端数据发送完
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("onNext: " + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            //客户端数据发送完，触发了该方法，该方法将要返回的数据发送给客户端
            @Override
            public void onCompleted() {
                StudentResponse studentResponse1 = StudentResponse.newBuilder()
                        .setName("张三").setAge(20).setCity("杭州").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder()
                        .setName("李四").setAge(30).setCity("北京").build();

                StudentResponseList studentResponseList = StudentResponseList.newBuilder()
                        .addStudentResponse(studentResponse1)
                        .addStudentResponse(studentResponse2).build();
                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();
            }
        };
    }
}
