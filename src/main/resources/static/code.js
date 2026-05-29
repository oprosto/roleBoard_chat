joinBtn.onclick = async function() {
    selectedChatId = document.getElementById("chatIdInput").value;
    nickname = document.getElementById("nicknameInput").value.trim();
    if (!selectedChatId || !nickname) {
        alert("Введите Chat ID и ник");
        return;
    }

    // 1️⃣ Получаем историю сообщений
    const response = await fetch(`http://localhost:8080/api/chat/${selectedChatId}/messages`);
    const history = await response.json();
    chatDiv.innerHTML = "";
    history.forEach(msg => {
        const msgElem = document.createElement("div");
        msgElem.classList.add("message");
        msgElem.classList.add(msg.username === nickname ? "self" : "other");
        msgElem.textContent = msg.username + ": " + msg.content;
        chatDiv.appendChild(msgElem);
    });
    chatDiv.scrollTop = chatDiv.scrollHeight;

    // 2️⃣ Подключаем STOMP
    const socket = new SockJS('http://localhost:8080/ws/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log("Connected: " + frame);

        stompClient.subscribe('/topic/messages.' + selectedChatId, function(message) {
            const msg = JSON.parse(message.body);
            const msgElem = document.createElement("div");
            msgElem.classList.add("message");
            msgElem.classList.add(msg.username === nickname ? "self" : "other");
            msgElem.textContent = msg.username + ": " + msg.content;
            chatDiv.appendChild(msgElem);
            chatDiv.scrollTop = chatDiv.scrollHeight;
        });
    });

    document.getElementById("login").style.display = "none";
    chatDiv.style.display = "flex";
    msgInput.style.display = "inline-block";
    sendBtn.style.display = "inline-block";
    msgInput.focus();
};