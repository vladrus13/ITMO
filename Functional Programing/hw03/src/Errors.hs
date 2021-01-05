{-# LANGUAGE InstanceSigs #-}

module Errors
  ( Error (..),
  )
where

-- | Error for real File System
data Error
  = DirectoryNotFound       {directory :: String}
  | FileNotFound            {file :: String}
  | Another                 {another :: String}
  | FileAlreadyExists       {file :: String}
  | DirectoryALreadyExists  {directory :: String}
  | UnexpectedFile          {file :: String}
  | UnexpectedDirectory     {directory :: String}
  | UnexpectedError

-- | Instance Show for Error
instance Show Error where
  show :: Error -> String
  show (DirectoryNotFound directory')        = "Directory " ++ directory' ++ " not found"
  show (FileNotFound file')                  = "File " ++ file' ++ " not found"
  show (FileAlreadyExists file')             = "File " ++ file' ++ " already exists"
  show (DirectoryALreadyExists directory')   = "Directory" ++ directory' ++ " already exists"
  show UnexpectedError                      = "Unexpected error"
  show (UnexpectedFile file')                = "Unexpected file " ++ file'
  show (UnexpectedDirectory directory')      = "Unexpected directory " ++ directory'
  show (Another another')                    = another'