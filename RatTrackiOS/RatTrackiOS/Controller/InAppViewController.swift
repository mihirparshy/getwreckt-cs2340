//
//  InAppViewController.swift
//  RatTrackiOS
//
//  Created by Akhil Kikkeri on 12/1/17.
//  Copyright © 2017 getwreckt. All rights reserved.
//

import Foundation
import UIKit
import Firebase

class InAppViewController: UIViewController {
    
    var screenHeight:CGFloat!
    var screenWidth:CGFloat!
    
    var txt:UITextView!
    var ref:DatabaseReference!
    var auth:Auth!
    
    var logoutBtn:UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.auth = Auth.auth()
        self.ref = Database.database().reference()
        
        self.view.backgroundColor = UIColor.white
        
        self.ref.child("users").observe(.value, with: {(snapshot) in
            let valueGet:[String:Any] = snapshot.childSnapshot(forPath: self.auth.currentUser!.uid).value as! [String:Any]
            let u = AppUser(fullName: valueGet["fullName"] as! String, userName: valueGet["userName"] as! String, userType: valueGet["userType"] as! String)
            Model.setCurrentUser(u: u)
            self.updateUI(fbu: u)
        })
        
        logoutBtn = UIButton(frame: CGRect(x: 0, y: screenHeight / 2 - screenHeight / 5 + screenHeight / 20 * 8, width: screenWidth, height: screenHeight / 20))
        logoutBtn.backgroundColor = UIColor.init(red: 39/255, green: 174/255, blue: 96/255, alpha: 1)
        logoutBtn.setTitleColor(UIColor.black, for: .normal)
        logoutBtn.setTitle("Logout", for: .normal)
        logoutBtn.addTarget(self, action: #selector(btnLogout), for: .touchUpInside)
        self.view.addSubview(logoutBtn)
    }
    
    func updateUI(fbu:AppUser) {
        
    }
    
    @objc
    func btnLogout() {
        logout()
    }
    
    func logout() {
        
    }
    
}
