<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Facebook Messenger</title>
  <link rel="stylesheet" href="../css/base.css">
  <link rel="stylesheet" href="../css/bootstrap.min.css">
  <link rel="stylesheet" href="../css/messenger.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet" />
</head>

<body>
  <section style="background-color: white;">
    <div class="container py-5">
      <h3>Messenger</h1>
        <div class="row">
          <div class="col-md-12">

            <div class="card" id="chat3">
              <div class="card-body">

                <div class="row">
                  <div class="col-md-6 col-lg-5 col-xl-4 mb-4 mb-md-0">

                    <div class="p-3">

                      <div class="input-group rounded mb-3" id="search-input">
                        <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search"
                          aria-describedby="search-addon" />
                        <span class="input-group-text border-0" id="search-addon">
                          <button type="submit" style="border-radius: 15px"><i class="fa fa-search"></i></button>
                        </span>
                      </div>

                      <div data-mdb-perfect-scrollbar-init style="position: relative; height: 400px">
                        <ul class="list-unstyled mb-0">
                          <li class="p-2 border-bottom">
                            <a href="#!" class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                                <div>
                                  <img
                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
                                    alt="avatar" class="d-flex align-self-center me-3" width="60">
                                  <span class="badge bg-success badge-dot"></span>
                                </div>
                                <div class="pt-1">
                                  <p class="fw-bold mb-0">Marie Horwitz</p>
                                  <p class="small text-muted">Hello, Are you there?</p>
                                </div>
                              </div>
                              <div class="pt-1">
                                <p class="small text-muted mb-1">Just now</p>
                                <span class="badge bg-danger rounded-pill float-end">3</span>
                              </div>
                            </a>
                          </li>
                          <li class="p-2 border-bottom">
                            <a href="#!" class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                                <div>
                                  <img
                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava2-bg.webp"
                                    alt="avatar" class="d-flex align-self-center me-3" width="60">
                                  <span class="badge bg-warning badge-dot"></span>
                                </div>
                                <div class="pt-1">
                                  <p class="fw-bold mb-0">Alexa Chung</p>
                                  <p class="small text-muted">Lorem ipsum dolor sit.</p>
                                </div>
                              </div>
                              <div class="pt-1">
                                <p class="small text-muted mb-1">5 mins ago</p>
                                <span class="badge bg-danger rounded-pill float-end">2</span>
                              </div>
                            </a>
                          </li>
                          <li class="p-2 border-bottom">
                            <a href="#!" class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                                <div>
                                  <img
                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
                                    alt="avatar" class="d-flex align-self-center me-3" width="60">
                                  <span class="badge bg-success badge-dot"></span>
                                </div>
                                <div class="pt-1">
                                  <p class="fw-bold mb-0">Danny McChain</p>
                                  <p class="small text-muted">Lorem ipsum dolor sit.</p>
                                </div>
                              </div>
                              <div class="pt-1">
                                <p class="small text-muted mb-1">Yesterday</p>
                              </div>
                            </a>
                          </li>
                          <li class="p-2 border-bottom">
                            <a href="#!" class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                                <div>
                                  <img
                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
                                    alt="avatar" class="d-flex align-self-center me-3" width="60">
                                  <span class="badge bg-danger badge-dot"></span>
                                </div>
                                <div class="pt-1">
                                  <p class="fw-bold mb-0">Ashley Olsen</p>
                                  <p class="small text-muted">Lorem ipsum dolor sit.</p>
                                </div>
                              </div>
                              <div class="pt-1">
                                <p class="small text-muted mb-1">Yesterday</p>
                              </div>
                            </a>
                          </li>
                          <li class="p-2 border-bottom">
                            <a href="#!" class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                                <div>
                                  <img
                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava5-bg.webp"
                                    alt="avatar" class="d-flex align-self-center me-3" width="60">
                                  <span class="badge bg-warning badge-dot"></span>
                                </div>
                                <div class="pt-1">
                                  <p class="fw-bold mb-0">Kate Moss</p>
                                  <p class="small text-muted">Lorem ipsum dolor sit.</p>
                                </div>
                              </div>
                              <div class="pt-1">
                                <p class="small text-muted mb-1">Yesterday</p>
                              </div>
                            </a>
                          </li>
                          <li class="p-2">
                            <a href="#!" class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                              </div>
                            </a>
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 col-lg-7 col-xl-8">

                    <div class="pt-3 pe-3" data-mdb-perfect-scrollbar-init style="position: relative; height: 400px">

                      <div class="d-flex flex-row justify-content-start">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
                          alt="avatar 1" style="width: 45px; height: 100%;">
                        <div>
                          <p class="small p-2 ms-3 mb-1 rounded-3" style="background-color: #f5f6f7;">Lorem ipsum
                            dolor
                            sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                            dolore
                            magna aliqua.</p>
                          <p class="small ms-3 mb-3 rounded-3 text-muted float-end">12:00 PM | Aug 13</p>
                        </div>
                      </div>

                      <div class="d-flex flex-row justify-content-end">
                        <div>
                          <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">Ut enim ad minim veniam,
                            quis
                            nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                          <p class="small me-3 mb-3 rounded-3 text-muted">12:00 PM | Aug 13</p>
                        </div>
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
                          alt="avatar 1" style="width: 45px; height: 100%;">
                      </div>
                      <div class="d-flex flex-row justify-content-end">
                        <div>
                          <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">Lorem ipsum dolor sit, amet
                            consectetur adipisicing elit. Vel explicabo voluptatem dolorum fugit perferendis iste neque
                            magnam quo aspernatur accusantium, repellendus, magni facere, est ea consequuntur! Harum
                            corrupti eaque facere?</p>
                          <p class="small me-3 mb-3 rounded-3 text-muted">12:00 PM | Aug 13</p>
                        </div>
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
                          alt="avatar 1" style="width: 45px; height: 100%;">
                      </div>

                    </div>

                    <div class="text-muted d-flex justify-content-start align-items-center pe-3 pt-3 mt-2">
                      <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
                        alt="avatar 3" style="width: 40px; height: 100%;">
                      <input type="text" class="form-control form-control-lg" id="exampleFormControlInput2"
                        placeholder="Type message">
                      <a class="ms-1 text-muted" href="#!"><i class="fas fa-paperclip"></i></a>
                      <a class="ms-3 text-muted" href="#!"><i class="fas fa-smile"></i></a>
                      <a class="ms-3 link-info" href="#!"><i class="fas fa-paper-plane"></i></a>
                    </div>

                  </div>
                </div>

              </div>
            </div>

          </div>
        </div>

    </div>
  </section>
</body>

</html>