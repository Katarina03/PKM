#include "prog_launch_server.h"

void handle_connection(SOCKET socket, sockaddr_in* addr) {
    char* str_in_addr = inet_ntoa(addr->sin_addr);
    printf("[%s]>>%s\n", str_in_addr, "Establish new connection");
	int rc = 1;
    while (rc > 0) {
        LaunchProgRequest request;
		memset(&request, sizeof(request), 0);

		LaunchProgResponse response;
		memset(&request, sizeof(request), 0);

        rc = recv(socket, (char*)&request, sizeof(request), 0);
		compute(&request, &response);
		rc = send(socket, (char*)&response, sizeof(response), 0); 

    }
    close_socket(socket);
    printf("[%s]>>%s", str_in_addr, "Close incomming connection");
}

LaunchProgResponse* compute(LaunchProgRequest* rq, LaunchProgResponse* rs) {
	char  psBuffer[128] = "";
	FILE   *pPipe;

	if (!(pPipe = _popen(rq->data, "r"))) {
		strcpy(rs->data, "Cant find programm !");
	}
	else {
		while (!feof(pPipe))
		{
			fgets(psBuffer, 128, pPipe);
				strcat(rs->data, psBuffer);
			memset(psBuffer, 0, sizeof(psBuffer));
		}
		_pclose(pPipe);
	}

	return rs;
}
